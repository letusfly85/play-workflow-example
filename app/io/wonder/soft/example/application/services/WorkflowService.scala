package io.wonder.soft.example.application.services

import io.wonder.soft.example.domain.workflow.{WorkflowFactory, WorkflowSchemeRepository, WorkflowStatusRepository}
import io.wonder.soft.example.domain.workflow.entity.{WorkflowSchemeEntity, WorkflowStatusEntity}

object WorkflowService {

  def createStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
      WorkflowStatusRepository.create(workflowStatusEntity)

  def updateStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
    WorkflowStatusRepository.update(workflowStatusEntity)

  def createScheme(workflowSchemeEntity: WorkflowSchemeEntity): Either[Exception, WorkflowSchemeEntity] = {
    val maybeEntity = WorkflowFactory.createSchemeEntity(workflowSchemeEntity)

    maybeEntity match {
      case Some(entity) =>
        WorkflowSchemeRepository.create(entity)

      case None =>
        Left(new RuntimeException(""))
    }
  }

}
