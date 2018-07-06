package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.ActionTransactionEntity
import io.wonder.soft.retail.domain.workflow.model.ActionTransactions
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class ActionTransactionRepositoryImpl extends ActionTransactionRepository {

  val atc = ActionTransactions.column

  override def find(id: Int): Option[ActionTransactionEntity] = ???

  override def create(entity: ActionTransactionEntity): Either[Exception, ActionTransactionEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(ActionTransactions).namedValues(
            atc.actionId -> entity.actionId,
            atc.workflowId -> entity.workflowId,
            atc.transactionId -> entity.transactionId,
            atc.stepId -> entity.stepId,
            atc.isReverted -> entity.isReverted
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  override def update(entity: ActionTransactionEntity): Either[RuntimeException, ActionTransactionEntity] = {
    Try {
      ActionTransactions.find(entity.id) match {
        case Some(transitions) =>
          transitions.copy(
            actionId = entity.actionId,
            workflowId = entity.workflowId,
            transactionId = entity.transactionId,
            stepId = entity.stepId,
            isReverted = entity.isReverted
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

  override def destroy(id: Int): Option[ActionTransactionEntity] = None

}
