package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowEntity
import io.wonder.soft.retail.domain.workflow.model.Workflows
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowRepositoryImpl extends WorkflowRepository {
  import WorkflowEntity._

  val wds = Workflows.column

  override def find(id: Int): Option[WorkflowEntity] = {
    Workflows.find(id) match {
      case Some(model) => Some(model)
      case None => None
    }
  }

  override def create(entity: WorkflowEntity): Either[Exception, WorkflowEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(Workflows).namedValues(
          wds.workflowId -> entity.workflowId,
             wds.name -> entity.name,
            wds.description -> entity.description,
             wds.serviceId -> entity.serviceId
             )
        }.update().apply()
      }
    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  override def update(entity: WorkflowEntity): Either[Exception, WorkflowEntity] = {
    Workflows.find(entity.id) match {
      case Some(model) =>
        model.copy(
          workflowId = entity.workflowId,
          name = entity.name,
          serviceId = entity.serviceId
        ).save()
        Right(model)

      case None => Left(new Exception(""))
    }
  }

  override def destroy(id: Int): Option[WorkflowEntity] = {
    Workflows.find(id) match {
      case Some(model) =>
        model.destroy()
        Some(model)

      case None => None
    }
  }

}
