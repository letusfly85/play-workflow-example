package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity._
import io.wonder.soft.example.domain.workflow.model.{WorkflowDefinitions, WorkflowStatuses, WorkflowTransitions}
import scalikejdbc._

object WorkflowQueryProcessor {
  import WorkflowStatusEntity._

  val wsm = WorkflowDefinitions.syntax("wsm")
  val wss = WorkflowStatuses.syntax("wss")
  val wts = WorkflowTransitions.syntax("wtc")

  def searchStatuses(): List[WorkflowStatusEntity] = {
    WorkflowStatuses.findAll()
  }

  def searchDefinitions(workflowId: Int): List[WorkflowDefinitionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowDefinitions as wsm)
          .innerJoin(WorkflowStatuses as wss)
          .on(wsm.statusId, wss.id)
          .where(sqls.eq(wsm.workflowId, workflowId))
      }.map(res => (WorkflowDefinitions(wsm)(res), WorkflowStatuses(wss)(res))).list().apply()
    }).map{ case (scheme: WorkflowDefinitions, status: WorkflowStatuses) =>
      WorkflowFactory.buildDefinitionEntity(scheme, status)
    }
  }

  def searchDefinitionsByDefinitionId(id: Int): Option[WorkflowDefinitionEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowDefinitions as wsm)
          .innerJoin(WorkflowStatuses as wss)
          .on(wsm.statusId, wss.id)
          .where(sqls.eq(wsm.id, id))
      }.map(res => (WorkflowDefinitions(wsm)(res), WorkflowStatuses(wss)(res))).single().apply()
    }).map{ case (scheme: WorkflowDefinitions, status: WorkflowStatuses) =>
      WorkflowFactory.buildDefinitionEntity(scheme, status)
    }
  }

  def searchTransitions(workflowId: Int): List[WorkflowTransitionEntity] =
    WorkflowTransitions.findAllBy(sqls.eq(wts.workflowId, workflowId))

}
