package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowSchemeEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowSchemes
import io.wonder.soft.example.domain.{Entity, Repository}
import scalikejdbc._

object WorkflowSchemeRepository extends Repository {
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

  override def create(entity: Entity): Either[Exception, WorkflowSchemeEntity] =
    Left(new RuntimeException(""))

  override def destroy(id: Int): Option[WorkflowSchemeEntity] = None

  override def update(entity: Entity): Either[Exception, WorkflowSchemeEntity] =
    Left(new RuntimeException(""))

}
