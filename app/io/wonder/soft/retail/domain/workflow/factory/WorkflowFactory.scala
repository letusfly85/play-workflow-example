package io.wonder.soft.retail.domain.workflow.factory

import io.wonder.soft.retail.domain.example.craft.entity.CraftLineActionEntity
import io.wonder.soft.retail.domain.workflow.entity._
import io.wonder.soft.retail.domain.workflow.model.Workflows

object WorkflowFactory {

  def build(workflows: Workflows): WorkflowEntity = {
    WorkflowEntity(
      id = workflows.id,
      workflowId = workflows.workflowId,
      name = workflows.name,
      description = workflows.description,
      steps = workflows.steps.map(wd => wd).toList,
      serviceId = workflows.serviceId
    )
  }

  def buildDefinitionEntity(schemeEntity: WorkflowStepEntity, statusEntity: WorkflowStatusEntity): WorkflowStepEntity = {
    schemeEntity.copy(status = Some(statusEntity))
  }

  def buildTransitionEntity(
                             transitionEntity: WorkflowTransitionEntity,
                             fromStep: WorkflowStepEntity,
                             toStep: WorkflowStepEntity): WorkflowTransitionEntity = {

    WorkflowTransitionEntity(
      transitionEntity.id,
      transitionEntity.workflowId,
      transitionEntity.name,
      Some(WorkflowStepEntity(
        workflowId = fromStep.workflowId,
        name = fromStep.name,
        status = None,
        stepId = fromStep.stepId,
        stepLabel = fromStep.stepLabel,
        isFirstStep = fromStep.isFirstStep,
        isLastStep = fromStep.isLastStep
      )),
      Some(WorkflowStepEntity(
        workflowId = fromStep.workflowId,
        name = toStep.name,
        status = None,
        stepId = toStep.stepId,
        stepLabel = toStep.stepLabel,
        isFirstStep = toStep.isFirstStep,
        isLastStep = toStep.isLastStep
      )),
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

  def buildConditionEntity(action: CraftLineActionEntity, maybeActionId: Option[Int], workflowId: Int, transitionId: Int): WorkflowActionConditionEntity = {
    WorkflowActionConditionEntity(
      0,
      name = Some(action.name),
      workflowId = workflowId,
      actionId = action.id,
      transitionId = transitionId,
      serviceId = action.serviceId,
      isActivate = maybeActionId.isDefined
    )
  }

}
