package io.wonder.soft.retail.domain.workflow.query

import io.wonder.soft.retail.domain.example.craft.model.CraftLineActions
import io.wonder.soft.retail.domain.workflow.entity.ActionTransactionEntity
import io.wonder.soft.retail.domain.workflow.model.{ActionTransactions, WorkflowActionConditions, WorkflowTransitions}
import scalikejdbc._
import io.wonder.soft.retail.domain.workflow.entity.WorkflowActionConditionEntity
import io.wonder.soft.retail.domain.example.craft.entity.CraftLineActionEntity
import io.wonder.soft.retail.domain.workflow.factory.WorkflowFactory

class ActionTransactionQueryProcessor {

  val atc = ActionTransactions.column

  val wac = WorkflowActionConditions.syntax("wac")
  val wt = WorkflowTransitions.syntax("wt")
  val cla = CraftLineActions.syntax("cla")

  val subWac = SubQuery.syntax("subwac").include(wac)

  import CraftLineActionEntity._
  import ActionTransactionEntity._
  import WorkflowActionConditionEntity._

  def findAllByTransactionId(transactionId: String, actionId: Int): List[ActionTransactionEntity] =
    ActionTransactions
      .findAllBy(
        sqls.eq(atc.transactionId, transactionId)
          .and.eq(atc.actionId, actionId)
          .and.ne(atc.isReverted, true))
      .map(t => t)

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
