package io.wonder.soft.retail.domain.workflow.query

import io.wonder.soft.retail.domain.workflow.entity._
import io.wonder.soft.retail.domain.workflow.model._
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowStatusEntity, WorkflowTransitionEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowFactory
import scalikejdbc._

class WorkflowQuery {
  import WorkflowStatusEntity._
  import WorkflowDetailEntity._

  // syntax
  val w = Workflows.syntax("w")
  val wd = WorkflowDetails.syntax("wd")
  val wd_to = WorkflowDetails.syntax("wd_to")
  val ws = WorkflowStatuses.syntax("ws")
  val wt = WorkflowTransitions.syntax("wt")

  def searchStatuses(): List[WorkflowStatusEntity] = {
    WorkflowStatuses.findAll()
  }

  def searchSummaries: List[WorkflowEntity] = {
    Workflows.findAll().map(s => s)
  }

  def findMaxSummaryWorkflowId: Int = {
    Workflows.findAll.maxBy(ws => ws.workflowId).workflowId
  }

  def search(workflowId: Int): Seq[Workflows] = {
    val (w, wd) = (Workflows.syntax, WorkflowDetails.syntax)

    (DB localTx { implicit session =>
      withSQL { select.from(Workflows as w).leftJoin(WorkflowDetails as wd).on(w.workflowId, wd.workflowId) }
        .one(Workflows(w))
        .toMany(WorkflowDetails.opt(wd))
        .map { (workflow, details) => workflow.copy(details = details) }
        .list.apply()
    })
  }

  def searchDefinitions(workflowId: Int): List[WorkflowDetailEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowDetails as wd)
          .innerJoin(WorkflowStatuses as ws)
          .on(wd.statusId, ws.id)
          .where(sqls.eq(wd.workflowId, workflowId))
      }.map(res => (WorkflowDetails(wd)(res), WorkflowStatuses(ws)(res))).list().apply()
    }).map{ case (scheme: WorkflowDetails, status: WorkflowStatuses) =>
      WorkflowFactory.buildDefinitionEntity(scheme, status)
    }
  }

  /*
  def searchDefinitionsByDefinitionId(id: Int): Option[WorkflowDefinitionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowDetails as wd)
          .innerJoin(WorkflowStatuses as ws)
          .on(wd.statusId, ws.id)
          .where(sqls.eq(wd.id, id))
      }.map(res => (WorkflowDetails(wd)(res), WorkflowStatuses(ws)(res))).single().apply()
    }).map{ case (scheme: WorkflowDetails, status: WorkflowStatuses) =>
      WorkflowFactory.buildDefinitionEntity(scheme, status)
    }
  }
  */

  def searchTransitions(workflowId: Int): List[WorkflowTransitionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowTransitions as wt)
          .innerJoin(WorkflowDetails as wd)
          .on(wt.fromStepId, wd.stepId)
          .innerJoin(WorkflowDetails as wd_to)
          .on(wt.toStepId, wd_to.stepId)
          .where(
            sqls.eq(wt.workflowId, workflowId)
              .and.eq(wd.workflowId, workflowId)
              .and.eq(wd_to.workflowId, workflowId)
          )
      }.map(res => (WorkflowTransitions(wt)(res), WorkflowDetails(wd)(res), WorkflowDetails(wd_to)(res))).list.apply
    }).map{ case (transition: WorkflowTransitions, fromStep: WorkflowDetails, toStep: WorkflowDetails) =>
      WorkflowFactory.buildTransitionEntity(
        transition, fromStep, toStep
      )
    }

  }

  def findDefine(workflowId: Int, stepId: Int): Option[WorkflowDetailEntity] = {
    WorkflowDetails.findBy(sqls.eq(wd.workflowId, workflowId).and.eq(wd.stepId, stepId)).map(d => d)
  }

}
