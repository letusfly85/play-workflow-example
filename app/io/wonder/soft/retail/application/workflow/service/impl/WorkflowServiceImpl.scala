package io.wonder.soft.retail.application.workflow.service.impl

import javax.inject.Inject
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowDefinitionSummaryEntity, WorkflowStatusEntity, WorkflowTransitionEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowFactory
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import io.wonder.soft.retail.domain.workflow.repository.{WorkflowDefinitionRepository, WorkflowStatusRepository, WorkflowTransitionRepository}
import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowService
import repository.WorkflowDefinitionSummaryRepository

class WorkflowServiceImpl @Inject()
(summaryRepository: WorkflowDefinitionSummaryRepository,
 workflowDefinitionRepository: WorkflowDefinitionRepository,
 workflowStatusRepository: WorkflowStatusRepository,
 workflowTransitionRepository: WorkflowTransitionRepository,
 queryProcessor: WorkflowQuery
)
  extends ApplicationService with WorkflowService {

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

  def createSummary(entity: WorkflowDefinitionSummaryEntity): Either[Exception, WorkflowDefinitionSummaryEntity] = {
    val nextWorkflowId = queryProcessor.findMaxSummaryWorkflowId + 1
    summaryRepository.create(entity.copy(workflowId = nextWorkflowId))
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
