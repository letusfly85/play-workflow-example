package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowStepEntity
import io.wonder.soft.retail.domain.workflow.model.WorkflowSteps
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowDetailRepositoryImpl extends WorkflowDetailRepository  {
  import WorkflowStepEntity._

  val wsc = WorkflowSteps.column

  override def find(id: Int): Option[WorkflowStepEntity] = {
    WorkflowSteps.find(id).map(definitions => definitions)
  }

  override def create(entity: WorkflowStepEntity): Either[Exception, WorkflowStepEntity] = {
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
  override def destroy(id: Int): Option[WorkflowStepEntity] = None

  //TODO
  override def update(entity: WorkflowStepEntity): Either[Exception, WorkflowStepEntity] = ???

}
