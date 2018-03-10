package io.wonder.soft.example.application.workflow.service

import io.wonder.soft.example.application.ApplicationService
import io.wonder.soft.example.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowStatusEntity}
import io.wonder.soft.example.domain.workflow.{WorkflowFactory, WorkflowQueryProcessor, WorkflowDefinitionRepository, WorkflowStatusRepository}

object WorkflowService extends ApplicationService {

  def listStatus: List[WorkflowStatusEntity] = {
    WorkflowQueryProcessor.searchStatuses
  }

  def createStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
      WorkflowStatusRepository.create(workflowStatusEntity)

  def updateStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
    WorkflowStatusRepository.update(workflowStatusEntity)

  def listDefinition(workflowId: Int): List[WorkflowDefinitionEntity] = {
    WorkflowQueryProcessor.searchDefinitions(workflowId)
  }

  def findDefinition(id: Int): Either[Exception, WorkflowDefinitionEntity] = {
    WorkflowQueryProcessor.searchDefinitionsByDefinitionId(id) match {
      case Some(entity) => Right(entity)
      case None => Left(new RuntimeException("not found scheme id"))
    }
  }

  def createDefinition(schemeEntity: WorkflowDefinitionEntity): Either[Exception, WorkflowDefinitionEntity] = {
    val maybeStatus = WorkflowStatusRepository.find(schemeEntity.status.get.id)

    maybeStatus match {
      case Some(statusEntity) =>
        val entity = WorkflowFactory.createDefinitionEntity(schemeEntity, statusEntity)
        WorkflowDefinitionRepository.create(entity)

      case None =>
        Left(new RuntimeException(""))
    }
  }

}
