package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowDefinitionEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowDefinitions
import io.wonder.soft.example.domain.Repository
import scalikejdbc._

import scala.util.{Failure, Success, Try}

object WorkflowDefinitionRepository extends Repository[WorkflowDefinitionEntity] {
  import WorkflowDefinitionEntity._

  val wsc = WorkflowDefinitions.column

  override def find(id: Int): Option[WorkflowDefinitionEntity] = {
    val maybeDefinitions = WorkflowDefinitions.find(id)
    maybeDefinitions.flatMap[WorkflowDefinitionEntity](schemes => Some(schemes))
  }

  def findStatusId(workflowId: Int): Option[Int] = {
    WorkflowDefinitions.findBy(
      sqls.eq(WorkflowDefinitions.column.workflowId, workflowId)).map(scheme => scheme.statusId
    )
  }

  override def create(entity: WorkflowDefinitionEntity): Either[Exception, WorkflowDefinitionEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowDefinitions).namedValues(
            wsc.workflowId -> entity.workflowId,
            wsc.name -> entity.name,
            wsc.statusId -> entity.status.get.id,
            wsc.schemeStepId -> entity.schemeStepId,
            wsc.schemeStepLabel -> entity.schemeStepLabel,
            wsc.isFirstStep -> entity.isFirstStep,
            wsc.isLastStep -> entity.isLastStep
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  //TODO
  override def destroy(id: Int): Option[WorkflowDefinitionEntity] = None

  //TODO
  override def update(entity: WorkflowDefinitionEntity): Either[Exception, WorkflowDefinitionEntity] = ???

}
