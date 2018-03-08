package io.wonder.soft.example.application.workflow.service

import io.wonder.soft.example.domain.workflow.entity.{WorkflowSchemeEntity, WorkflowStatusEntity}
import io.wonder.soft.example.domain.workflow.{WorkflowFactory, WorkflowQueryProcessor, WorkflowSchemeRepository, WorkflowStatusRepository}

object WorkflowService {

  def listStatus: List[WorkflowStatusEntity] = {
    WorkflowQueryProcessor.searchStatuses
  }

  def createStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
      WorkflowStatusRepository.create(workflowStatusEntity)

  def updateStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
    WorkflowStatusRepository.update(workflowStatusEntity)

  def listScheme(workflowId: Int): List[WorkflowSchemeEntity] = {
    WorkflowQueryProcessor.searchSchemes(workflowId)
  }

  def findScheme(id: Int): Either[Exception, WorkflowSchemeEntity] = {
    WorkflowQueryProcessor.searchSchemesBySchemeId(id) match {
      case Some(entity) => Right(entity)
      case None => Left(new RuntimeException("not found scheme id"))
    }
  }

  def createScheme(schemeEntity: WorkflowSchemeEntity): Either[Exception, WorkflowSchemeEntity] = {
    val maybeStatus = WorkflowStatusRepository.find(schemeEntity.status.get.id)

    maybeStatus match {
      case Some(statusEntity) =>
        val entity = WorkflowFactory.createSchemeEntity(schemeEntity, statusEntity)
        WorkflowSchemeRepository.create(entity)

      case None =>
        Left(new RuntimeException(""))
    }
  }

}
