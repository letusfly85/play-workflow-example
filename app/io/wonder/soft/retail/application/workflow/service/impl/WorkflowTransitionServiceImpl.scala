package io.wonder.soft.retail.application.workflow.service.impl

import io.wonder.soft.retail.application.workflow.service.WorkflowTransitionService
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowTransitionEntity => TransitionEntity}
import io.wonder.soft.retail.domain.workflow.repository.WorkflowTransitionRepository
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import javax.inject.Inject

class WorkflowTransitionServiceImpl @Inject() (
  query: WorkflowQuery,
  workflowTransitionRepository: WorkflowTransitionRepository) extends WorkflowTransitionService {

  def listTransition(workflowId: Int): List[TransitionEntity] =
    query.searchTransitions(workflowId)

  //TODO implement
  def findTransition(workflowId: Int, fromStepId: Int, toStepId: Int): Either[Exception, TransitionEntity] =
    Left(new RuntimeException(""))

  def createTransition(transitionEntity: TransitionEntity): Either[Exception, TransitionEntity] =
    workflowTransitionRepository.create(transitionEntity)

  //TODO
  def updateTransition(transitionEntity: TransitionEntity): Either[Exception, TransitionEntity] = ???

  //TODO
  def destroyTransition(transitionEntity: TransitionEntity): Either[Exception, TransitionEntity] = ???

}
