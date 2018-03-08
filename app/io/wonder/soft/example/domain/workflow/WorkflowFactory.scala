package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.{WorkflowSchemeEntity, WorkflowStatusEntity}

object WorkflowFactory {

  def createSchemeEntity(workflowId: Int): Option[WorkflowSchemeEntity] = {
    val maybeSchemeEntity = WorkflowSchemeRepository.find(workflowId)
    val maybeStatusId = WorkflowSchemeRepository.findStatusId(workflowId)

    (maybeSchemeEntity, maybeStatusId) match {
      case (Some(schemes), Some(statusId)) =>
        val maybeStatusEntity = WorkflowStatusRepository.find(statusId)
        maybeStatusEntity match {
          case Some(status) => Some(schemes.copy(status = Some(status)))
          case None => None
        }

      case (_, _) => None
    }
  }

  def createSchemeEntity(workflowSchemeEntity: WorkflowSchemeEntity): Option[WorkflowSchemeEntity] = {
    val maybeStatus = WorkflowStatusRepository.find(workflowSchemeEntity.status.get.id)

    maybeStatus.flatMap(status => Some(workflowSchemeEntity.copy(status = Some(status))))
  }

  def createSchemeEntity(schemeEntity: WorkflowSchemeEntity, statusEntity: WorkflowStatusEntity): WorkflowSchemeEntity = {
    schemeEntity.copy(status = Some(statusEntity))
  }

}
