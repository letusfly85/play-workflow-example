package io.wonder.soft.retail.domain.workflow.factory

import io.wonder.soft.retail.domain.workflow.entity._

object WorkflowFactory {

  def buildDefinitionEntity(schemeEntity: WorkflowDefinitionEntity, statusEntity: WorkflowStatusEntity): WorkflowDefinitionEntity = {
    schemeEntity.copy(status = Some(statusEntity))
  }

  def buildTransitionEntity(
    transitionEntity: WorkflowTransitionEntity,
    fromStep: WorkflowDefinitionEntity,
    toStep: WorkflowDefinitionEntity): WorkflowTransitionEntity = {

    WorkflowTransitionEntity(
      transitionEntity.id,
      transitionEntity.workflowId,
      transitionEntity.name,
      WorkflowStepEntity(
        stepId = fromStep.stepId, stepLabel = fromStep.stepLabel,
        isFirstStep = fromStep.isFirstStep, isLastStep = fromStep.isLastStep
      ),
      WorkflowStepEntity(
        stepId = toStep.stepId, stepLabel = toStep.stepLabel,
        isFirstStep = toStep.isFirstStep, isLastStep = toStep.isLastStep
      ),
      transitionEntity.conditionSuiteId,
      transitionEntity.isDefined
    )
  }

  def buildConditionEntity(actionTransition: WorkflowActionTransitionEntity): WorkflowActionConditionEntity = {
    WorkflowActionConditionEntity(
      0,
      name = None,
      workflowId = actionTransition.transitionEntity.workflowId,
      actionId = actionTransition.actionId,
      transitionId = actionTransition.transitionEntity.id,
      serviceId = actionTransition.serviceId
    )
  }

}
