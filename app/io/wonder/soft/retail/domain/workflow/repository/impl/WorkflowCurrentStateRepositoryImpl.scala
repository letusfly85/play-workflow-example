package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowCurrentStateEntity
import io.wonder.soft.retail.domain.workflow.model.WorkflowCurrentStates
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowCurrentStateRepositoryImpl extends WorkflowCurrentStateRepository {

  val wcsc = WorkflowCurrentStates.column

  override def find(id: Int): Option[WorkflowCurrentStateEntity] = ???

  override def create(entity: WorkflowCurrentStateEntity)
    : Either[Exception, WorkflowCurrentStateEntity] = {
    Try {
      DB localTx { implicit session =>
        withSQL {
          insert
            .into(WorkflowCurrentStates)
            .namedValues(
              wcsc.workflowId -> entity.workflowId,
              wcsc.transactionId -> entity.transactionId,
              wcsc.currentStepId -> entity.currentStepId,
              wcsc.serviceId -> entity.serviceId,
              wcsc.userId -> entity.userId
            )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  override def update(entity: WorkflowCurrentStateEntity)
    : Either[RuntimeException, WorkflowCurrentStateEntity] = {
    Try {
      WorkflowCurrentStates.findBy(
        sqls
          .eq(wcsc.id, entity.id)) match {
        case Some(transitions) =>
          transitions.copy(currentStepId = entity.currentStepId).save()
          Right(entity)

        case None =>
          Left(new RuntimeException("")) //TODO
      }

    } match {
      case Success(result) => result
      case Failure(e)      => Left(new RuntimeException(e))
    }
  }

  override def destroy(id: Int): Option[WorkflowCurrentStateEntity] = None

}
