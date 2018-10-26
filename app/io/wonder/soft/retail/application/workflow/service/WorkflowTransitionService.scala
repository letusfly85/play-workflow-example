package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowTransitionEntity => TransitionEntity}

trait WorkflowTransitionService {

  def listTransition(workflowId: Int): List[TransitionEntity]

  def findTransition(workflowId: Int, fromStepId: Int, toStepId: Int): Either[Exception, TransitionEntity]

  def createTransition(transitionEntity: TransitionEntity): Either[Exception, TransitionEntity]

  def updateTransition(transitionEntity: TransitionEntity): Either[Exception, TransitionEntity]

  def destroyTransition(workflowId: Int, transitionId: Int): Either[Exception, TransitionEntity]

}
