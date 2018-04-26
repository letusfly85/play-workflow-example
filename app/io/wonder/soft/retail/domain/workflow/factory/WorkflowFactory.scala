package io.wonder.soft.retail.domain.workflow.factory

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowStatusEntity, WorkflowStepEntity, WorkflowTransitionEntity}

object WorkflowFactory {

  def buildDefinitionEntity(schemeEntity: WorkflowDefinitionEntity, statusEntity: WorkflowStatusEntity): WorkflowDefinitionEntity = {
    schemeEntity.copy(status = Some(statusEntity))
  }

  def buildTransitionEntity(
    transitionEntity: WorkflowTransitionEntity,
    fromStep: WorkflowDefinitionEntity,
    toStep: WorkflowDefinitionEntity): WorkflowTransitionEntity = {

    WorkflowTransitionEntity(
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

}
