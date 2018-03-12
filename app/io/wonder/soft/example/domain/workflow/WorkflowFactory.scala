package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowStatusEntity}

object WorkflowFactory {

  def buildDefinitionEntity(schemeEntity: WorkflowDefinitionEntity, statusEntity: WorkflowStatusEntity): WorkflowDefinitionEntity = {
    schemeEntity.copy(status = Some(statusEntity))
  }

}
