package io.wonder.soft.example.domain.workflow.repository

import io.wonder.soft.example.domain.Repository
import io.wonder.soft.example.domain.workflow.entity.WorkflowTransactionEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowTransactions
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowTransactionRepository extends Repository[WorkflowTransactionEntity] {

  val wtc = WorkflowTransactions.column

  override def find(id: Int): Option[WorkflowTransactionEntity] = ???

  override def create(entity: WorkflowTransactionEntity): Either[Exception, WorkflowTransactionEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowTransactions).namedValues(
            wtc.workflowId -> entity.workflowId,
            wtc.transactionId -> entity.transactionId,
            wtc.userId -> entity.userId,
            wtc.stepId -> entity.stepId,
            wtc.fromTransitionId -> entity.fromTransitionId
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  override def update(entity: WorkflowTransactionEntity): Either[RuntimeException, WorkflowTransactionEntity] = {
    Try {
      WorkflowTransactions.find(entity.id) match {
        case Some(transitions) =>
          transitions.copy(
            workflowId = entity.workflowId,
            transactionId = entity.transactionId,
            userId = entity.userId,
            stepId = entity.stepId,
            fromTransitionId = entity.fromTransitionId,
            isInit = entity.isInit,
            isCompleted = entity.isCompleted,
            updatedAt = Some(new org.joda.time.DateTime())
          ).save()
          Right(entity)

        case None =>
          Left(new RuntimeException("")) //TODO
      }

    } match {
      case Success(result) => result
      case Failure(e) => Left(new RuntimeException(e))
    }
  }

  override def destroy(id: Int): Option[WorkflowTransactionEntity] = None

}
