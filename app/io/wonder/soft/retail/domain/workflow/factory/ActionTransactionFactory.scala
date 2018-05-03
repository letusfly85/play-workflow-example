package io.wonder.soft.retail.domain.workflow.factory

import io.wonder.soft.retail.domain.workflow.entity.{ActionTransactionEntity, WorkflowActionConditionEntity, WorkflowCurrentStateEntity}

object ActionTransactionFactory {

  def build(actionId: Int, currentState: WorkflowCurrentStateEntity, isReverted: Boolean = false): ActionTransactionEntity = {
    ActionTransactionEntity(
      id = 0,
      workflowId = currentState.workflowId,
      transactionId = currentState.transactionId,
      stepId = currentState.currentStepId,
      actionId = actionId,
      isReverted = isReverted
    )
  }

  def buildByAction(condition: WorkflowActionConditionEntity, maybeStepId: Option[Int], transactionId: String): ActionTransactionEntity = {
    ActionTransactionEntity(
      id = condition.id,
      workflowId = condition.workflowId,
      transactionId = transactionId,
      stepId = maybeStepId.getOrElse(0),
      actionId = condition.actionId,
      isReverted = false,
      isFinished = maybeStepId.isDefined
    )
  }

}
