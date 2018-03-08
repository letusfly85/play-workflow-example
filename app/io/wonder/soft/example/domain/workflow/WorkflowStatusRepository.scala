package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.Repository
import io.wonder.soft.example.domain.workflow.entity.WorkflowStatusEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowStatuses
import scalikejdbc._

import scala.util.{Failure, Success, Try}

object WorkflowStatusRepository extends Repository[WorkflowStatusEntity] {
  val wsc = WorkflowStatuses.column

  import WorkflowStatusEntity._

  override def find(id: Int): Option[WorkflowStatusEntity] =
    WorkflowStatuses.find(id).flatMap(status => Some(status))

  override def create(entity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowStatuses).namedValues(
            wsc.name -> entity.name
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  override def update(entity: WorkflowStatusEntity): Either[RuntimeException, WorkflowStatusEntity] = {
    Try {
      WorkflowStatuses.find(entity.id) match {
        case Some(statuses) =>
          statuses.copy(name = entity.name).save()
          Right(entity)

        case None =>
          Left(new RuntimeException("")) //TODO
      }

    } match {
      case Success(result) => result
      case Failure(e) => Left(new RuntimeException(e))
    }
  }

  override def destroy(id: Int): Option[WorkflowStatusEntity] = None
}
