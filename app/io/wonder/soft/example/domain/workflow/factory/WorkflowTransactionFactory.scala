package io.wonder.soft.example.domain.workflow.factory

import entity.WorkflowCurrentStateEntity
import io.wonder.soft.example.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowTransactionEntity}

object WorkflowTransactionFactory {

  def build(userId: String,
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


}
