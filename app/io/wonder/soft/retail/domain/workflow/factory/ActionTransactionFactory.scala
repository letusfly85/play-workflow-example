package io.wonder.soft.retail.domain.workflow.factory

import io.wonder.soft.retail.domain.workflow.entity.{ActionTransactionEntity, WorkflowCurrentStateEntity}

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

}
