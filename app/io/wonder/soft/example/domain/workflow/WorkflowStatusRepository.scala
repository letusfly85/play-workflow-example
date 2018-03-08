package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowStatusEntity
import io.wonder.soft.example.domain.{Entity, Repository}
import io.wonder.soft.example.domain.workflow.model.WorkflowStatuses
import scalikejdbc._

import scala.util.{Failure, Success, Try}

object WorkflowStatusRepository extends Repository {
  import WorkflowStatusEntity._

  val wsc = WorkflowStatuses.column
  override def find(id: Int): Option[WorkflowStatusEntity] = {
    WorkflowStatuses.find(id) match {
      case Some(status) => Some(status)
      case None => None
    }
  }

  override def create(entity: Entity): Either[Throwable, WorkflowStatusEntity] = {
    val status = entity.asInstanceOf[WorkflowStatusEntity]
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowStatuses).namedValues(
            wsc.name -> status.name
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(status)
      case Failure(e) => Left(e)
    }
  }

  override def destroy(id: Int): Option[WorkflowStatusEntity] = None

  //TODO
  override def update(entity: Entity): Either[Throwable, WorkflowStatusEntity] = ???

}
