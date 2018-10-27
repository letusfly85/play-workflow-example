package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowDetailEntity
import io.wonder.soft.retail.domain.workflow.model.WorkflowSteps
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowDetailRepositoryImpl extends WorkflowDetailRepository  {
  import WorkflowDetailEntity._

  val wsc = WorkflowSteps.column

  override def find(id: Int): Option[WorkflowDetailEntity] = {
    WorkflowSteps.find(id).map(definitions => definitions)
  }

  override def create(entity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowSteps).namedValues(
            wsc.workflowId -> entity.workflowId,
            wsc.name -> entity.name,
            wsc.statusId -> entity.status.get.id,
            wsc.stepId -> entity.stepId,
            wsc.stepLabel -> entity.stepLabel,
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
  override def destroy(id: Int): Option[WorkflowDetailEntity] = None

  //TODO
  override def update(entity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity] = ???

}
