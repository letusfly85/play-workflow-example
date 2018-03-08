package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowStatusEntity
import io.wonder.soft.example.domain.{Entity, Repository}
import io.wonder.soft.example.domain.workflow.model.WorkflowStatuses

import scala.util.{Failure, Success, Try}

object WorkflowStatusRepository extends Repository {
  import WorkflowStatusEntity._

  override def find(id: Int): Option[WorkflowStatusEntity] = {
    WorkflowStatuses.find(id) match {
      case Some(status) => Some(status)
      case None => None
    }
  }

  override def create(entity: Entity): Either[Throwable, WorkflowStatusEntity] = {
    val status = entity.asInstanceOf[WorkflowStatusEntity]
    Try {
      WorkflowStatuses.create(name = status.name).save()

    } match {
      case Success(_) => Right(status)
      case Failure(e) => Left(e)
    }
  }

  override def destroy(id: Int): Option[WorkflowStatusEntity] = None

  //TODO
  override def update(entity: Entity): Either[Throwable, WorkflowStatusEntity] = ???

}
