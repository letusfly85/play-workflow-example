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
  val wd = WorkflowSteps.syntax("wd")
  val wd_to = WorkflowSteps.syntax("wd_to")
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

  def list: List[WorkflowEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(Workflows as w)
      }.map(rs => (Workflows(w)(rs)))
        .list.apply()
    })
  }

  def find(workflowId: Int): Option[WorkflowEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.all(w, wd).from(Workflows as w)
          .leftJoin(WorkflowSteps as wd).on(w.workflowId, wd.workflowId)
          .where(sqls.eq(w.workflowId, workflowId))
      }.one(Workflows(w))
        .toMany(WorkflowSteps.opt(wd))
        .map { (workflow, steps) => workflow.copy(steps = steps) }
        .single.apply()
    }).map { case workflows: Workflows =>
        WorkflowFactory.build(workflows)
    }
  }

  def searchDefinitions(workflowId: Int): List[WorkflowDetailEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowSteps as wd)
          .innerJoin(WorkflowStatuses as ws)
          .on(wd.statusId, ws.id)
          .where(sqls.eq(wd.workflowId, workflowId))
      }.map(res => (WorkflowSteps(wd)(res), WorkflowStatuses(ws)(res))).list().apply()
    }).map{ case (scheme: WorkflowSteps, status: WorkflowStatuses) =>
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
          .innerJoin(WorkflowSteps as wd)
          .on(wt.fromStepId, wd.stepId)
          .innerJoin(WorkflowSteps as wd_to)
          .on(wt.toStepId, wd_to.stepId)
          .where(
            sqls.eq(wt.workflowId, workflowId)
              .and.eq(wd.workflowId, workflowId)
              .and.eq(wd_to.workflowId, workflowId)
          )
      }.map(res => (WorkflowTransitions(wt)(res), WorkflowSteps(wd)(res), WorkflowSteps(wd_to)(res))).list.apply
    }).map{ case (transition: WorkflowTransitions, fromStep: WorkflowSteps, toStep: WorkflowSteps) =>
      WorkflowFactory.buildTransitionEntity(
        transition, fromStep, toStep
      )
    }

  }

  def findDefine(workflowId: Int, stepId: Int): Option[WorkflowDetailEntity] = {
    WorkflowSteps.findBy(sqls.eq(wd.workflowId, workflowId).and.eq(wd.stepId, stepId)).map(d => d)
  }

}
