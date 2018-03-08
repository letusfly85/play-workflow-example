package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.{WorkflowSchemeEntity, WorkflowStatusEntity}

object WorkflowFactory {

  def createSchemeEntity(schemeEntity: WorkflowSchemeEntity, statusEntity: WorkflowStatusEntity): WorkflowSchemeEntity = {
    schemeEntity.copy(status = Some(statusEntity))
  }

}
