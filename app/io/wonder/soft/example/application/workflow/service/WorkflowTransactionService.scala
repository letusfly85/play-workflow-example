package io.wonder.soft.example.application.workflow.service

import entity.WorkflowCurrentStateEntity
import io.wonder.soft.example.application.ApplicationService
import io.wonder.soft.example.domain.workflow.entity.{WorkflowTransactionEntity, WorkflowTransitionEntity}
import io.wonder.soft.example.domain.workflow.factory.WorkflowTransactionFactory
import io.wonder.soft.example.domain.workflow.query.{WorkflowQueryProcessor, WorkflowTransactionQueryProcessor}
import io.wonder.soft.example.domain.workflow.repository.{WorkflowCurrentStateRepository, WorkflowDefinitionRepository, WorkflowTransactionRepository}
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
          WorkflowTransactionFactory.buildTransaction(userId, transactionId, define)
        val initialTransaction = transaction.copy(isInit = true)
        transactionRepository.create(initialTransaction)

      case None =>
        Left(
          new RuntimeException(
            s"there is no initial step for workflowId: ${workflowId}"))
    }
  }

  def proceedState(stateEntity: WorkflowCurrentStateEntity, transitionEntity: WorkflowTransitionEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    // todo build transaction entity from state and transition

    // todo record transaction

    // todo return new entity
    Right(stateEntity)
  }

  def recordTransaction(entity: WorkflowTransactionEntity)
    : Either[Exception, WorkflowTransactionEntity] = {
    //todo get from transition id
    transactionQueryProcessor.findCurrentStateByTransactionId(entity.transactionId) match {
      case Some(currentStateEntity) =>
        //todo

      case None =>
        //todo
    }

    //todo copy instance

    //flush to database
    transactionRepository.create(entity)
  }

  def isLastStep(workflowId: Int, stepId: Int): Either[Exception, Boolean] = {
    val defines = defineQuery.searchDefinitions(workflowId)
    val errorMessage = "there is no last step."
    defines
      .filter(d => d.stepId == stepId)
      .headOption
      .map(p => p.isLastStep)
      .toRight(new RuntimeException(
        s"{workflow_id: ${workflowId}, step_id: ${stepId}, message: ${errorMessage}")
      )
  }
}
