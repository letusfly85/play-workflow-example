package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowTransactionEntity, WorkflowUserTransitionEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowTransactionFactory
import io.wonder.soft.retail.domain.workflow.query.{ActionTransactionQueryProcessor, WorkflowQueryProcessor, WorkflowTransactionQueryProcessor}
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
    transactionQuery: WorkflowTransactionQueryProcessor,
    transactionRepository: WorkflowTransactionRepository,
    actionProcessor: ActionTransactionQueryProcessor,
    currentStateRepository: WorkflowCurrentStateRepository)
    extends ApplicationService {

  def openTransaction(userId: String, workflowId: Int): Either[Exception, WorkflowTransactionEntity] = {
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

        val currentState =
          WorkflowTransactionFactory.buildCurrentState(
            userId,
            transactionId,
            define,
            serviceId)

        currentStateRepository.create(currentState)

        val transaction =
          WorkflowTransactionFactory.buildTransaction(
            userId,
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

  def findDefinitionByStepId(workflowId: Int, stepId: Int): Option[WorkflowDefinitionEntity] = {
    defineQuery.findDefine(workflowId, stepId)
  }

  def proceedState(transactionId: String, transition: WorkflowTransitionEntity): Either[Exception, WorkflowCurrentStateEntity] = {
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
          proceedAppTransaction(currentStateEntity)
        }
        result

      case None =>
        Left(new RuntimeException(s"not found current state for ${transactionId}"))
    }
  }

  private def proceedAppTransaction(currentStateEntity: WorkflowCurrentStateEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    val define = findDefinitionByStepId(currentStateEntity.workflowId, currentStateEntity.currentStepId).get
    userTransaction.updateAppTransaction(define, currentStateEntity)
  }

  private def recordTransaction(entity: WorkflowTransactionEntity): Either[Exception, WorkflowTransactionEntity] = {
    //flush to database
    transactionRepository.create(entity)
  }

  private def closeTransaction(
    currentState: WorkflowCurrentStateEntity,
    transition: WorkflowTransitionEntity): Either[Exception, WorkflowCurrentStateEntity] = {

    Try {
      WorkflowTransactionFactory.buildFinishedState(currentState, transition)
    } match {
      case Success(result)    => Right(result)
      case Failure(exception) => Left(new Exception(exception))
    }
  }

  private def generateNextState(
    currentState: WorkflowCurrentStateEntity,
    transition: WorkflowTransitionEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    Try {
      //todo validate whether state have next step or not

      WorkflowTransactionFactory.buildNextState(currentState, transition)
    } match {
      case Success(result)    => Right(result)
      case Failure(exception) => Left(new Exception(exception))
    }
  }

  def listTransition(workflowId: Int, transactionId: String): List[WorkflowUserTransitionEntity] = {
    val workflowTransitions = defineQuery.searchTransitions(workflowId)
    val currentState = transactionQuery.findCurrentStateByTransactionId(transactionId)

    val userTransitions = WorkflowTransactionFactory.buildUserTransitions(currentState, workflowTransitions)
    userTransitions.map {userTransition =>
      val actionTransactions = actionProcessor.findByTransitionAndTransactionId(transactionId, userTransition.transitionEntity.id)

      println(s"${userTransition.transitionEntity.id}, ${actionTransactions.length}")
      userTransition.copy(actionTransactions = actionTransactions)
    }
  }

}
