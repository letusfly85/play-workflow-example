package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowSchemeEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowSchemes

object WorkflowFactory {

  def createSchemeEntityFromModel(model: WorkflowSchemes): Option[WorkflowSchemeEntity] = {
    import WorkflowSchemeEntity._
    val maybeStatusEntity = WorkflowStatusRepository.find(model.statusId)
    maybeStatusEntity match {
      case Some(status) =>
        Some(new WorkflowSchemeEntity(
          workflowId = model.workflowId,
          name = model.name,
          status = status,
          schemeStepId = model.schemeStepId,
          schemeStepLabel = model.schemeStepLabel,
          isFirstStep = model.isFirstStep,
          isLastStep = model.isLastStep
        ))
      case None => None
    }
  }

}
