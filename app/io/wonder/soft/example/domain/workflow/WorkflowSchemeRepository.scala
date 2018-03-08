package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowSchemeEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowSchemes
import io.wonder.soft.example.domain.Repository
import scalikejdbc._

object WorkflowSchemeRepository extends Repository[WorkflowSchemeEntity] {
  import WorkflowSchemeEntity._

  override def find(workflowId: Int): Option[WorkflowSchemeEntity] = {
    val maybeSchemes = WorkflowSchemes.findBy(sqls.eq(WorkflowSchemes.column.workflowId, workflowId))
    maybeSchemes.flatMap[WorkflowSchemeEntity](schemes => Some(schemes))
  }

  def findStatusId(workflowId: Int): Option[Int] = {
    WorkflowSchemes.findBy(
      sqls.eq(WorkflowSchemes.column.workflowId, workflowId)).map(scheme => scheme.statusId
    )
  }

  //TODO
  override def create(entity: WorkflowSchemeEntity): Either[Exception, WorkflowSchemeEntity] = ???

  //TODO
  override def destroy(id: Int): Option[WorkflowSchemeEntity] = None

  //TODO
  override def update(entity: WorkflowSchemeEntity): Either[Exception, WorkflowSchemeEntity] = ???

}
