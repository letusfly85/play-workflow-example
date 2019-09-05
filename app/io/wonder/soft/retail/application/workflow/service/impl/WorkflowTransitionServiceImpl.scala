package io.wonder.soft.retail.application.workflow.service.impl

import io.wonder.soft.retail.application.workflow.service.WorkflowTransitionService
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowTransitionEntity => TransitionEntity}
import io.wonder.soft.retail.domain.workflow.repository.WorkflowTransitionRepository
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import javax.inject.Inject
import play.api.Logging

import scala.util.{Failure, Success, Try}

class WorkflowTransitionServiceImpl @Inject() (
  query: WorkflowQuery,
  transitionRepository: WorkflowTransitionRepository) extends WorkflowTransitionService with Logging {

  def listTransition(workflowId: Int): List[TransitionEntity] =
    query.searchTransitions(workflowId)

  //TODO implement
  def findTransition(workflowId: Int, fromStepId: Int, toStepId: Int): Either[Exception, TransitionEntity] =
    Left(new RuntimeException(""))

  def createTransition(transitionEntity: TransitionEntity): Either[Exception, TransitionEntity] =
    transitionRepository.create(transitionEntity)

  //TODO
  def updateTransition(transitionEntity: TransitionEntity): Either[Exception, TransitionEntity] = ???

  def destroyTransition(workflowId: Int, transitionId: Int): Either[Exception, TransitionEntity] = {
    Try {
      logger.info(s"destroying ${workflowId}, ${transitionId}")
      transitionRepository.destroy(transitionId)
    } match {
      case Success(Some(entity)) => Right(entity)
      case Success(None) => Left(new RuntimeException(s"NOT_FOUND transition: ${transitionId}"))
      case Failure(ex) => Left(new RuntimeException(ex))
    }
  }

}
