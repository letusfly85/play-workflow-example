package io.wonder.soft.retail.application.workflow.service.impl

import javax.inject.Inject
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDetailEntity, WorkflowStatusEntity, WorkflowEntity => DefinitionSummaryEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowFactory
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import io.wonder.soft.retail.domain.workflow.repository.{WorkflowDetailRepositoryImpl, WorkflowStatusRepositoryImpl}
import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowService
import repository.WorkflowRepositoryImpl

import scala.util.{Failure, Success, Try}

class WorkflowServiceImpl @Inject()
(summaryRepository: WorkflowRepositoryImpl,
 workflowRepository: WorkflowDetailRepositoryImpl,
 workflowStatusRepository: WorkflowStatusRepositoryImpl,
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

  def listDefinition(workflowId: Int): List[WorkflowDetailEntity] = {
    queryProcessor.searchDefinitions(workflowId)
  }

  def listSummary: List[DefinitionSummaryEntity] = {
    queryProcessor.searchSummaries
  }

  def createSummary(entity: DefinitionSummaryEntity): Either[Exception, DefinitionSummaryEntity] = {
    val nextWorkflowId = queryProcessor.findMaxSummaryWorkflowId + 1
    summaryRepository.create(entity.copy(workflowId = nextWorkflowId))
  }

  def destroySummary(entity: DefinitionSummaryEntity): Either[Exception, DefinitionSummaryEntity] = {
    Try {
      //TODO use WorkflowDefinitionRepositoryImpl repository
      summaryRepository.destroy(entity.id)

    } match {
      case Success(_) => Right(entity)
      case Failure(ex) => Left(new RuntimeException(ex))
    }
  }

  /*
  def findDefinition(id: Int): Either[Exception, WorkflowDefinitionEntity] = {
    queryProcessor.searchDefinitionsByDefinitionId(id) match {
      case Some(entity) => Right(entity)
      case None => Left(new RuntimeException("not found scheme id"))
    }
  }
  */

  def createDefinition(schemeEntity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity] = {
    val maybeStatus = workflowStatusRepository.find(schemeEntity.status.get.id)

    maybeStatus match {
      case Some(statusEntity) =>
        val entity = WorkflowFactory.buildDefinitionEntity(schemeEntity, statusEntity)
        workflowRepository.create(entity)

      case None =>
        Left(new RuntimeException(""))
    }
  }

}
