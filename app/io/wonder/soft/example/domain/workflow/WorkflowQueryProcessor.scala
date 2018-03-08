package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity._
import io.wonder.soft.example.domain.workflow.model.{WorkflowSchemes, WorkflowStatuses}
import scalikejdbc._

object WorkflowQueryProcessor {
  import WorkflowStatusEntity._

  val wsm = WorkflowSchemes.syntax("wsm")
  val wss = WorkflowStatuses.syntax("wss")

  def searchStatuses(): List[WorkflowStatusEntity] = {
    WorkflowStatuses.findAll()
  }

  def searchSchemes(workflowId: Int): List[WorkflowSchemeEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowSchemes as wsm)
          .innerJoin(WorkflowStatuses as wss)
          .on(wsm.statusId, wss.id)
          .where(sqls.eq(wsm.workflowId, workflowId))
      }.map(res => (WorkflowSchemes(wsm)(res), WorkflowStatuses(wss)(res))).list().apply()
    }).map{ case (scheme: WorkflowSchemes, status: WorkflowStatuses) =>
      WorkflowFactory.createSchemeEntity(scheme, status)
    }
  }

  def searchSchemesBySchemeId(id: Int): Option[WorkflowSchemeEntity] = {
    (DB localTx { implicit session =>
      withSQL {
        select.from(WorkflowSchemes as wsm)
          .innerJoin(WorkflowStatuses as wss)
          .on(wsm.statusId, wss.id)
          .where(sqls.eq(wsm.id, id))
      }.map(res => (WorkflowSchemes(wsm)(res), WorkflowStatuses(wss)(res))).single().apply()
    }).map{ case (scheme: WorkflowSchemes, status: WorkflowStatuses) =>
      WorkflowFactory.createSchemeEntity(scheme, status)
    }
  }

}
