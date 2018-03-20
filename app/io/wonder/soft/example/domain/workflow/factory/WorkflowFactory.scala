package io.wonder.soft.example.domain.workflow.factory

import io.wonder.soft.example.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowStatusEntity, WorkflowStepEntity, WorkflowTransitionEntity}

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
        schemeStepId = fromStep.schemeStepId, schemeStepLabel = fromStep.schemeStepLabel,
        isFirstStep = fromStep.isFirstStep, isLastStep = fromStep.isLastStep
      ),
      WorkflowStepEntity(
        schemeStepId = toStep.schemeStepId, schemeStepLabel = toStep.schemeStepLabel,
        isFirstStep = toStep.isFirstStep, isLastStep = toStep.isLastStep
      ),
      transitionEntity.conditionSuiteId,
      transitionEntity.isDefined
    )
  }

}
