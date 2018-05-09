package repository

import io.wonder.soft.retail.domain.Repository
import io.wonder.soft.retail.domain.workflow.entity.WorkflowDefinitionSummaryEntity
import io.wonder.soft.retail.domain.workflow.model.WorkflowDefinitionSummaries
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowDefinitionSummaryRepository extends Repository[WorkflowDefinitionSummaryEntity] {
  import WorkflowDefinitionSummaryEntity._

  val wds = WorkflowDefinitionSummaries.syntax("wds")

  override def find(id: Int): Option[WorkflowDefinitionSummaryEntity] = {
    WorkflowDefinitionSummaries.find(id) match {
      case Some(model) => Some(model)
      case None => None
    }
  }

  override def create(entity: WorkflowDefinitionSummaryEntity): Either[Exception, WorkflowDefinitionSummaryEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowDefinitionSummaries).namedValues(
          wds.workflowId -> entity.workflowId,
             wds.name -> entity.name,
             wds.serviceId -> entity.serviceId
             )
        }.update().apply()
      }
    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  override def update(entity: WorkflowDefinitionSummaryEntity): Either[Exception, WorkflowDefinitionSummaryEntity] = {
    WorkflowDefinitionSummaries.find(entity.id) match {
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

  override def destroy(id: Int): Option[WorkflowDefinitionSummaryEntity] = {
    WorkflowDefinitionSummaries.find(id) match {
      case Some(model) =>
        model.destroy()
        Some(model)

      case None => None
    }
  }

}
