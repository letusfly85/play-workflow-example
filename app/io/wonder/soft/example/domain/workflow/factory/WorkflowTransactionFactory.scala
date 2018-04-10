package io.wonder.soft.example.domain.workflow.factory

import entity.WorkflowCurrentStateEntity
import io.wonder.soft.example.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowTransactionEntity, WorkflowTransitionEntity}

object WorkflowTransactionFactory {

  def buildTransaction(userId: String,
                       transactionId: String,
                       define: WorkflowDefinitionEntity): WorkflowTransactionEntity = {

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
      fromTransitionId = Some(transition.fromStep.schemeStepId),
      isInit = false,
      isCompleted = transition.toStep.isLastStep
    )
  }

  def buildCurrentState(userId: String,
            transactionId: String,
            define: WorkflowDefinitionEntity): WorkflowCurrentStateEntity = {
    WorkflowCurrentStateEntity(
      id = 0,
      workflowId = define.workflowId,
      transactionId = transactionId,
      userId = Some(userId),
      currentStepId = define.stepId,
      schemeId = 0,
      serviceId = 0
    )
  }

  def buildNextState(currentState: WorkflowCurrentStateEntity, transition: WorkflowTransitionEntity): WorkflowCurrentStateEntity = {
    WorkflowCurrentStateEntity(
      id = 0,
      workflowId = currentState.workflowId,
      transactionId = currentState.transactionId,
      userId = currentState.userId,
      currentStepId = transition.toStep.schemeStepId,
      schemeId = 0,
      serviceId = 0
    )
  }

  def buildFinishedState(currentState: WorkflowCurrentStateEntity, transition: WorkflowTransitionEntity): WorkflowCurrentStateEntity = {
    WorkflowCurrentStateEntity(
      id = 0,
      workflowId = currentState.workflowId,
      transactionId = currentState.transactionId,
      userId = currentState.userId,
      currentStepId = transition.toStep.schemeStepId,
      isFinished = true,
      schemeId = 0,
      serviceId = 0
    )
  }
}
