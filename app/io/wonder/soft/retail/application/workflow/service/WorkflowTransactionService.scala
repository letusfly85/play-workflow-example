package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowTransactionEntity, WorkflowUserTransitionEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowTransactionFactory
import io.wonder.soft.retail.domain.workflow.query.{WorkflowQueryProcessor, WorkflowTransactionQueryProcessor}
import io.wonder.soft.retail.domain.workflow.repository.WorkflowDefinitionRepository
import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowCurrentStateEntity, WorkflowTransitionEntity}
import io.wonder.soft.retail.domain.workflow.repository.{WorkflowCurrentStateRepository, WorkflowTransactionRepository}
import io.wonder.soft.retail.domain.workflow.service.UserTransaction
import javax.inject.Inject
import play.api.Logger

import scala.util.{Failure, Success, Try}

class WorkflowTransactionService @Inject()(
    userTransaction: UserTransaction,
    defineQuery: WorkflowQueryProcessor,
    definitionRepository: WorkflowDefinitionRepository,
    transactionQuery: WorkflowTransactionQueryProcessor,
    transactionRepository: WorkflowTransactionRepository,
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

        val serviceId =
        if (workflowId == 3) 3
        else 0
        val currentState = WorkflowTransactionFactory.buildCurrentState(
          userId,
          transactionId,
          define, serviceId)
        currentStateRepository.create(currentState)

        val transaction =
          WorkflowTransactionFactory.buildTransaction(userId,
                                                      transactionId,
                                                      define)
        val initialTransaction = transaction.copy(isInit = true)
        transactionRepository.create(initialTransaction)

      case None =>
        Left(
          new RuntimeException(
            s"there is no initial step for workflowId: ${workflowId}"))
    }
  }

  def showDefine(workflowId: Int, stepId: Int): Option[WorkflowDefinitionEntity] = {
    defineQuery.findDefine(workflowId, stepId)
  }

  def closeTransaction(currentState: WorkflowCurrentStateEntity,
                       transition: WorkflowTransitionEntity)
    : Either[Exception, WorkflowCurrentStateEntity] = {
    Try {
      WorkflowTransactionFactory.buildFinishedState(currentState, transition)
    } match {
      case Success(result)    => Right(result)
      case Failure(exception) => Left(new Exception(exception))
    }
  }

  def proceedState(transactionId: String,
                   transition: WorkflowTransitionEntity)
    : Either[Exception, WorkflowCurrentStateEntity] = {
    // build transaction entity from state and transition
    val maybeCurrentState = transactionQuery.findCurrentStateByTransactionId(transactionId)

    maybeCurrentState match {
      case Some(currentState) =>
        Logger.info(currentState.toString)
        val transaction =
          WorkflowTransactionFactory.buildTransaction(currentState, transition)
        recordTransaction(transaction)
        val result = transition.toStep.isLastStep match {
          case true =>
            closeTransaction(currentState, transition)

          case false =>
            generateNextState(currentState, transition)
        }
        result.map{ currentStateEntity =>
          Logger.info(currentStateEntity.toString)
          currentStateRepository.update(currentStateEntity)
          updateUserRepository(currentStateEntity)
        }
        result

      case None =>
        Left(new RuntimeException(s"not found current state for ${transactionId}"))
    }
  }

  /**
    * update user workflow transaction results
    *
    * @param currentStateEntity
    * @return
    */
  def updateUserRepository(currentStateEntity: WorkflowCurrentStateEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    val define = showDefine(currentStateEntity.workflowId, currentStateEntity.currentStepId).get
    userTransaction.updateUserRepository(define, currentStateEntity)
  }

  def recordTransaction(entity: WorkflowTransactionEntity)
    : Either[Exception, WorkflowTransactionEntity] = {
    //flush to database
    transactionRepository.create(entity)
  }

  def generateNextState(currentState: WorkflowCurrentStateEntity,
                        transition: WorkflowTransitionEntity)
    : Either[Exception, WorkflowCurrentStateEntity] = {
    Try {
      //todo validate whether state have next step or not

      WorkflowTransactionFactory.buildNextState(currentState, transition)
    } match {
      case Success(result)    => Right(result)
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
        s"{workflow_id: ${workflowId}, step_id: ${stepId}, message: ${errorMessage}"))
  }

  def isFinished(workflowId: Int, transactionId: String): Boolean = {
    transactionQuery
      .findFinishedTransaction(transactionId)
      .map(t => t.isCompleted)
      .getOrElse(false)
  }

  def listTransition(workflowId: Int, transactionId: String): List[WorkflowUserTransitionEntity] = {
    val workflowTransitions = defineQuery.searchTransitions(workflowId)
    val currentState = transactionQuery.findCurrentStateByTransactionId(transactionId)
    Logger.info(transactionId)
    Logger.info(currentState.toString)
    Logger.info(workflowTransitions.toString)

    val userTransitions = WorkflowTransactionFactory.buildUserTransitions(currentState, workflowTransitions)
    userTransitions
  }

}
