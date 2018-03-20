package io.wonder.soft.example.domain.workflow.query

import io.wonder.soft.example.domain.workflow.entity._
import io.wonder.soft.example.domain.workflow.factory.WorkflowFactory
import io.wonder.soft.example.domain.workflow.model.{WorkflowDefinitions, WorkflowStatuses, WorkflowTransitions}
import scalikejdbc._

object WorkflowQueryProcessor {
  import WorkflowStatusEntity._

  // syntax
  val wd = WorkflowDefinitions.syntax("wd")
  val wd_to = WorkflowDefinitions.syntax("wd_to")
  val ws = WorkflowStatuses.syntax("ws")
  val wt = WorkflowTransitions.syntax("wt")

  def searchStatuses(): List[WorkflowStatusEntity] = {
    WorkflowStatuses.findAll()
  }

  def searchDefinitions(workflowId: Int): List[WorkflowDefinitionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowDefinitions as wd)
          .innerJoin(WorkflowStatuses as ws)
          .on(wd.statusId, ws.id)
          .where(sqls.eq(wd.workflowId, workflowId))
      }.map(res => (WorkflowDefinitions(wd)(res), WorkflowStatuses(ws)(res))).list().apply()
    }).map{ case (scheme: WorkflowDefinitions, status: WorkflowStatuses) =>
      WorkflowFactory.buildDefinitionEntity(scheme, status)
    }
  }

  def searchDefinitionsByDefinitionId(id: Int): Option[WorkflowDefinitionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowDefinitions as wd)
          .innerJoin(WorkflowStatuses as ws)
          .on(wd.statusId, ws.id)
          .where(sqls.eq(wd.id, id))
      }.map(res => (WorkflowDefinitions(wd)(res), WorkflowStatuses(ws)(res))).single().apply()
    }).map{ case (scheme: WorkflowDefinitions, status: WorkflowStatuses) =>
      WorkflowFactory.buildDefinitionEntity(scheme, status)
    }
  }

  def searchTransitions(workflowId: Int): List[WorkflowTransitionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowTransitions as wt)
          .innerJoin(WorkflowDefinitions as wd)
          .on(wt.fromStepId, wd.schemeStepId)
          .innerJoin(WorkflowDefinitions as wd_to)
          .on(wt.toStepId, wd_to.schemeStepId)
      }.map(res => (WorkflowTransitions(wt)(res), WorkflowDefinitions(wd)(res), WorkflowDefinitions(wd_to)(res))).list.apply
    }).map{ case (transition: WorkflowTransitions, fromStep: WorkflowDefinitions, toStep: WorkflowDefinitions) =>
      WorkflowFactory.buildTransitionEntity(
        transition, fromStep, toStep
      )
    }

  }

}
