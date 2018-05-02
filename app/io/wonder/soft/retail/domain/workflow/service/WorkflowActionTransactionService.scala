package io.wonder.soft.retail.domain.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.ActionTransactionEntity
import io.wonder.soft.retail.domain.workflow.factory.ActionTransactionFactory
import io.wonder.soft.retail.domain.workflow.query.{ActionTransactionQueryProcessor, WorkflowTransactionQueryProcessor}
import io.wonder.soft.retail.domain.workflow.repository.ActionTransactionRepository
import javax.inject.Inject

class WorkflowActionTransactionService @Inject()
  (transactionQuery: WorkflowTransactionQueryProcessor,
   actionQuery: ActionTransactionQueryProcessor,
   actionRepository: ActionTransactionRepository) {

  def saveActionTransaction(transactionId: String, actionId: Int): Either[Exception, ActionTransactionEntity] = {
    for {
      currentState <- transactionQuery.findCurrentStateByTransactionId(transactionId).toRight(new RuntimeException(""))
      actionEntity = ActionTransactionFactory.build(actionId, currentState)
      result <- actionRepository.create(actionEntity)
    } yield result
  }

  def revertActionTransaction(transactionId: String, actionId: Int): Either[Exception, ActionTransactionEntity] = {
    for {
      currentState <- transactionQuery.findCurrentStateByTransactionId(transactionId).toRight(new RuntimeException(""))
      actionEntity = ActionTransactionFactory.build(actionId, currentState, true)
      result <- actionRepository.update(actionEntity)
    } yield result
  }

}
