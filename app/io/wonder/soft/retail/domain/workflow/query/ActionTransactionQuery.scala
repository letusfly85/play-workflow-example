package io.wonder.soft.retail.domain.workflow.query

import io.wonder.soft.retail.domain.example.craft.model.CraftLineActions
import io.wonder.soft.retail.domain.workflow.entity.ActionTransactionEntity
import io.wonder.soft.retail.domain.workflow.model.{ActionTransactions, WorkflowActionConditions, WorkflowTransitions}
import scalikejdbc._
import io.wonder.soft.retail.domain.workflow.entity.WorkflowActionConditionEntity
import io.wonder.soft.retail.domain.example.craft.entity.CraftLineActionEntity
import io.wonder.soft.retail.domain.workflow.factory.{ActionTransactionFactory, WorkflowFactory}

class ActionTransactionQuery {

  val atc = ActionTransactions.column
  val at = ActionTransactions.syntax("at")

  val wac = WorkflowActionConditions.syntax("wac")
  val wt = WorkflowTransitions.syntax("wt")
  val cla = CraftLineActions.syntax("cla")

  val subWac = SubQuery.syntax("subwac").include(wac)
  val subAt = SubQuery.syntax("subat").include(at)

  import CraftLineActionEntity._
  import ActionTransactionEntity._
  import WorkflowActionConditionEntity._

  def findAllByActionId(transactionId: String, actionId: Int): List[ActionTransactionEntity] =
    ActionTransactions
      .findAllBy(
        sqls.eq(atc.transactionId, transactionId)
          .and.eq(atc.actionId, actionId)
          .and.ne(atc.isReverted, true))
      .map(t => t)

  def findByTransitionAndTransactionId(transactionId: String, transitionId: Int): List[ActionTransactionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.all(wac, subAt).from(WorkflowActionConditions as wac)
          .leftJoin(
            { select.all(at).from(ActionTransactions as at)
              .where(sqls.eq(at.transactionId, transactionId))
            }.as(subAt)
          )
          .on(wac.actionId, subAt(at).actionId)
          .where(
            sqls.eq(wac.transitionId, transitionId)
          )
      }.map { res =>
        ( WorkflowActionConditions(wac)(res), res.intOpt(subAt(at).resultName.stepId)  )
      }.list.apply
    }).map{ case (condition, maybeStepId) =>
      ActionTransactionFactory.buildByAction(condition, maybeStepId, transactionId)
    }
  }

  def findByTransitionId(actionId: Int, transitionId: Int): Option[WorkflowActionConditionEntity] = {
    WorkflowActionConditions
      .findBy(
        sqls.eq(wac.actionId, actionId)
          .and.eq(wac.transitionId, transitionId)
      ).map(t => t)
  }

  def searchCraftLineActions(workflowId: Int, transitionId: Int): List[WorkflowActionConditionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.all(cla, subWac).from(CraftLineActions as cla)
          .leftJoin(
            {
              select.all(wac).from(WorkflowActionConditions as wac)
                .where(sqls.eq(wac.workflowId, workflowId).and.eq(wac.transitionId, transitionId))
            }
              .as(subWac)
          )
          .on(cla.id, subWac(wac).actionId)
      }.map { res =>
        (CraftLineActions(cla)(res), res.intOpt((subWac(wac).resultName.actionId)))
      }.list.apply
    }).map{ case (action, maybeActionId) =>
      WorkflowFactory.buildConditionEntity(action, maybeActionId, workflowId, transitionId)
    }
  }
}
