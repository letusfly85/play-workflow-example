package io.wonder.soft.retail.application.workflow.service

import javax.inject.Inject
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowDefinitionSummaryEntity, WorkflowStatusEntity, WorkflowTransitionEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowFactory
import io.wonder.soft.retail.domain.workflow.query.WorkflowQueryProcessor
import io.wonder.soft.retail.domain.workflow.repository.{WorkflowDefinitionRepository, WorkflowStatusRepository, WorkflowTransitionRepository}
import io.wonder.soft.retail.application.ApplicationService

class WorkflowService @Inject()
  (workflowStatusRepository: WorkflowStatusRepository,
   workflowTransitionRepository: WorkflowTransitionRepository,
   queryProcessor: WorkflowQueryProcessor,
   workflowDefinitionRepository: WorkflowDefinitionRepository)
  extends ApplicationService {

  def listStatus: List[WorkflowStatusEntity] = {
    queryProcessor.searchStatuses
  }

  def createStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
      workflowStatusRepository.create(workflowStatusEntity)

  def updateStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
    workflowStatusRepository.update(workflowStatusEntity)

  def listDefinition(workflowId: Int): List[WorkflowDefinitionEntity] = {
    queryProcessor.searchDefinitions(workflowId)
  }

  def listSummary: List[WorkflowDefinitionSummaryEntity] = {
    queryProcessor.searchSummaries
  }

  def findDefinition(id: Int): Either[Exception, WorkflowDefinitionEntity] = {
    queryProcessor.searchDefinitionsByDefinitionId(id) match {
      case Some(entity) => Right(entity)
      case None => Left(new RuntimeException("not found scheme id"))
    }
  }

  def createDefinition(schemeEntity: WorkflowDefinitionEntity): Either[Exception, WorkflowDefinitionEntity] = {
    val maybeStatus = workflowStatusRepository.find(schemeEntity.status.get.id)

    maybeStatus match {
      case Some(statusEntity) =>
        val entity = WorkflowFactory.buildDefinitionEntity(schemeEntity, statusEntity)
        workflowDefinitionRepository.create(entity)

      case None =>
        Left(new RuntimeException(""))
    }
  }

  def listTransition(workflowId: Int): List[WorkflowTransitionEntity] =
    queryProcessor.searchTransitions(workflowId)

  //TODO implement
  def findTransition(workflowId: Int, fromStepId: Int, toStepId: Int): Either[Exception, WorkflowTransitionEntity] =
    Left(new RuntimeException(""))

  def createTransition(transitionEntity: WorkflowTransitionEntity): Either[Exception, WorkflowTransitionEntity] =
    workflowTransitionRepository.create(transitionEntity)

}
