package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowStatusEntity
import io.wonder.soft.example.domain.{Entity, Repository}
import io.wonder.soft.example.domain.workflow.model.WorkflowStatuses

object WorkflowStatusRepository extends Repository {
  import WorkflowStatusEntity._

  override def find(id: Int): Option[WorkflowStatusEntity] = {
    WorkflowStatuses.find(id) match {
      case Some(status) => Some(status)
      case None => None
    }
  }

  override def create(entity: Entity): Either[Exception, WorkflowStatusEntity] =
    Left(new RuntimeException(""))

  override def destroy(id: Int): Option[WorkflowStatusEntity] = None

  override def update(entity: Entity): Either[Exception, WorkflowStatusEntity] =
    Left(new RuntimeException(""))

}
