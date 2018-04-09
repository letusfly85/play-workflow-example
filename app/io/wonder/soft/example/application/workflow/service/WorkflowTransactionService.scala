package io.wonder.soft.example.application.workflow.service

import io.wonder.soft.example.application.ApplicationService
import io.wonder.soft.example.domain.workflow.entity.WorkflowTransactionEntity
import io.wonder.soft.example.domain.workflow.factory.WorkflowTransactionFactory
import io.wonder.soft.example.domain.workflow.query.{
  WorkflowQueryProcessor,
  WorkflowTransactionQueryProcessor
}
import io.wonder.soft.example.domain.workflow.repository.{
  WorkflowCurrentStateRepository,
  WorkflowDefinitionRepository,
  WorkflowTransactionRepository
}
import javax.inject.Inject

class WorkflowTransactionService @Inject()(
    defineQuery: WorkflowQueryProcessor,
    definitionRepository: WorkflowDefinitionRepository,
    transactionQueryProcessor: WorkflowTransactionQueryProcessor,
    transactionRepository: WorkflowTransactionRepository,
    transactionService: WorkflowTransactionService,
    currentStateRepository: WorkflowCurrentStateRepository)
    extends ApplicationService {

  def initialize(
      userId: String,
      workflowId: Int): Either[Exception, WorkflowTransactionEntity] = {
    //find workflow definition
    val defines = defineQuery.searchDefinitions(workflowId)
    val maybeInitialDefine = defines.filter(d => d.isFirstStep).headOption
    maybeInitialDefine match {
      case Some(define) =>
        //generate transaction id
        val transactionId = java.util.UUID.randomUUID().toString

        val currentState = WorkflowTransactionFactory.buildCurrentState(
          userId,
          transactionId,
          define)
        currentStateRepository.create(currentState)

        val transaction =
          WorkflowTransactionFactory.build(userId, transactionId, define)
        val initialTransaction = transaction.copy(isInit = true)
        transactionRepository.create(initialTransaction)

      case None =>
        Left(
          new RuntimeException(
            s"there is no initial step for workflowId: ${workflowId}"))
    }
  }

  def recordTransaction(entity: WorkflowTransactionEntity)
    : Either[Exception, WorkflowTransactionEntity] = {
    //todo check is last step
    val defines = defineQuery.searchDefinitions(entity.workflowId)
    val isLastStep = defines
      .filter(d => d.stepId == entity.stepId)
      .headOption
      .map(p => p.isLastStep)

    //todo get from transition id
    transactionQueryProcessor.findCurrentStateByTransactionId(entity.transactionId) match {
      case Some(transaction) =>
        //todo

      case None =>
        //todo
    }

    //todo copy instance

    //flush to database
    transactionRepository.create(entity)
  }

}
