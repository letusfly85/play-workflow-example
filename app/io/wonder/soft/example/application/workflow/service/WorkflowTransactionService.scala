package io.wonder.soft.example.application.workflow.service

import entity.WorkflowCurrentStateEntity
import io.wonder.soft.example.application.ApplicationService
import io.wonder.soft.example.domain.workflow.entity.{WorkflowTransactionEntity, WorkflowTransitionEntity}
import io.wonder.soft.example.domain.workflow.factory.WorkflowTransactionFactory
import io.wonder.soft.example.domain.workflow.query.{WorkflowQueryProcessor, WorkflowTransactionQueryProcessor}
import io.wonder.soft.example.domain.workflow.repository.{WorkflowCurrentStateRepository, WorkflowDefinitionRepository, WorkflowTransactionRepository}
import javax.inject.Inject

import scala.util.{Failure, Success, Try}

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

  def closeTransaction(currentState: WorkflowCurrentStateEntity, transition: WorkflowTransitionEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    Try {
      WorkflowTransactionFactory.buildFinishedState(currentState, transition)
    } match {
      case Success(result) => Right(result)
      case Failure(exception) => Left(new Exception(exception))
    }
  }

  def proceedState(currentState: WorkflowCurrentStateEntity, transition: WorkflowTransitionEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    // build transaction entity from state and transition
    val transaction = WorkflowTransactionFactory.buildTransaction(currentState, transition)
    recordTransaction(transaction)

    transition.toStep.isLastStep match {
      case true =>
        closeTransaction(currentState, transition)

      case false =>
        generateNextState(currentState, transition)
    }
  }

  def recordTransaction(entity: WorkflowTransactionEntity)
    : Either[Exception, WorkflowTransactionEntity] = {
    //flush to database
    transactionRepository.create(entity)
  }

  def generateNextState(currentState: WorkflowCurrentStateEntity, transition: WorkflowTransitionEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    Try {
      //todo validate whether state have next step or not

      WorkflowTransactionFactory.buildNextState(currentState, transition)
    } match {
      case Success(result) => Right(result)
      case Failure(exception) => Left(new Exception(exception))
    }
  }

  def isLast(workflowId: Int, stepId: Int): Either[Exception, Boolean] = {
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

  def isFinished(workflowId: Int, transactionId: String): Boolean = {
    transactionQueryProcessor.findFinishedTransaction(transactionId)
  }
}
