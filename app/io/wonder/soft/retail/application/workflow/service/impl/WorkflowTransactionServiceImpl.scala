package io.wonder.soft.retail.application.workflow.service.impl

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowTransactionService

import io.wonder.soft.retail.domain.workflow.entity._
import io.wonder.soft.retail.domain.workflow.factory.WorkflowTransactionFactory
import io.wonder.soft.retail.domain.workflow.query.{ActionTransactionQuery, WorkflowQuery, WorkflowTransactionQuery}
import io.wonder.soft.retail.domain.workflow.repository.{WorkflowCurrentStateRepositoryImpl, WorkflowTransactionRepositoryImpl}
import io.wonder.soft.retail.domain.workflow.service.ApplicationTransactionService

import javax.inject.Inject
import play.api.Logger

import scala.util.{Failure, Success, Try}

class WorkflowTransactionServiceImpl @Inject() (
                                                 appTransactionService: ApplicationTransactionService,
                                                 defineQuery: WorkflowQuery,
                                                 transactionQuery: WorkflowTransactionQuery,
                                                 transactionRepository: WorkflowTransactionRepositoryImpl,
                                                 actionProcessor: ActionTransactionQuery,
                                                 currentStateRepository: WorkflowCurrentStateRepositoryImpl)
    extends ApplicationService with WorkflowTransactionService {

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

  def findStep(workflowId: Int, stepId: Int): Option[WorkflowStepEntity] = {
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
        val result = transition.toStep.get.isLastStep match {
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
    val define = findStep(currentStateEntity.workflowId, currentStateEntity.currentStepId).get
    appTransactionService.updateAppTransaction(define, currentStateEntity)
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
