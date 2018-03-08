package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowSchemeEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowSchemes
import io.wonder.soft.example.domain.{Entity, Repository}
import scalikejdbc._

object WorkflowSchemeRepository extends Repository {

  override def find(id: Int): Option[WorkflowSchemeEntity] = {
    val maybeSchemes = WorkflowSchemes.findBy(sqls.eq(WorkflowSchemes.column.workflowId, id))
    maybeSchemes match {
      case Some(schemes) =>
        WorkflowFactory.createSchemeEntityFromModel(schemes)

      case None => None
    }
  }

  override def create(entity: Entity): Either[Exception, WorkflowSchemeEntity] =
    Left(new RuntimeException(""))

  override def destroy(id: Int): Option[WorkflowSchemeEntity] = None

  override def update(entity: Entity): Either[Exception, WorkflowSchemeEntity] =
    Left(new RuntimeException(""))

}
