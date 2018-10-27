package io.wonder.soft.retail.domain.workflow.factory

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowStepEntity, WorkflowTransactionEntity, WorkflowTransitionEntity, WorkflowUserTransitionEntity}
import io.wonder.soft.retail.domain.workflow.entity._

object WorkflowTransactionFactory {

  def buildTransaction(userId: String,
                       transactionId: String,
                       define: WorkflowStepEntity): WorkflowTransactionEntity = {

    WorkflowTransactionEntity(
      id = 0,
      workflowId = define.workflowId,
      transactionId = transactionId,
      userId = Some(userId),
      stepId = define.stepId,
      fromTransitionId = None,
      isInit = false,
      isCompleted = false
    )
  }

  def buildTransaction(currentState: WorkflowCurrentStateEntity, transition: WorkflowTransitionEntity): WorkflowTransactionEntity = {
    WorkflowTransactionEntity(
      id = 0,
      workflowId = currentState.workflowId,
      transactionId = currentState.transactionId,
      userId = currentState.userId,
      stepId = currentState.currentStepId,
      fromTransitionId = Some(transition.fromStep.get.stepId),
      isInit = false,
      isCompleted = transition.toStep.get.isLastStep
    )
  }

  def buildCurrentState(userId: String,
                        transactionId: String,
                        define: WorkflowStepEntity,
                        serviceId: Int = 0): WorkflowCurrentStateEntity = {
    WorkflowCurrentStateEntity(
      id = 0,
      workflowId = define.workflowId,
      transactionId = transactionId,
      userId = Some(userId),
      currentStepId = define.stepId,
      schemeId = 0,
      serviceId = serviceId
    )
  }

  def buildNextState(currentState: WorkflowCurrentStateEntity, transition: WorkflowTransitionEntity): WorkflowCurrentStateEntity = {
    WorkflowCurrentStateEntity(
      id = currentState.id,
      workflowId = currentState.workflowId,
      transactionId = currentState.transactionId,
      userId = currentState.userId,
      currentStepId = transition.toStep.get.stepId,
      schemeId = 0,
      serviceId = currentState.serviceId
    )
  }

  def buildFinishedState(currentState: WorkflowCurrentStateEntity, transition: WorkflowTransitionEntity): WorkflowCurrentStateEntity = {
    WorkflowCurrentStateEntity(
      id = currentState.id,
      workflowId = currentState.workflowId,
      transactionId = currentState.transactionId,
      userId = currentState.userId,
      currentStepId = transition.toStep.get.stepId,
      isFinished = true,
      schemeId = 0,
      serviceId = 0
    )
  }

  def buildUserTransitions(
      maybeCurrentState: Option[WorkflowCurrentStateEntity],
      workflowTransitions: List[WorkflowTransitionEntity]
    ): List[WorkflowUserTransitionEntity] = {


    workflowTransitions.map { transition =>
      val isActive =
        maybeCurrentState match {
          case Some(currentState) if currentState.currentStepId == transition.fromStep.get.stepId =>
            true
          case _ => false
        }

      WorkflowUserTransitionEntity(
        isActive = isActive,
        transitionEntity = transition,
        List.empty[ActionTransactionEntity]
      )
    }
  }
}
