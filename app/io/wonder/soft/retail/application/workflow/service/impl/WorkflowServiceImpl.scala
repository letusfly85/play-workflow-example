package io.wonder.soft.retail.application.workflow.service.impl

import javax.inject.Inject
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDetailEntity, WorkflowEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowFactory
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import io.wonder.soft.retail.domain.workflow.repository.{WorkflowDetailRepository, WorkflowRepository, WorkflowStatusRepository}
import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowService

import scala.util.{Failure, Success, Try}

class WorkflowServiceImpl @Inject()
(
  summaryRepository: WorkflowRepository,
  workflowRepository: WorkflowDetailRepository,
  workflowStatusRepository: WorkflowStatusRepository,
  query: WorkflowQuery
) extends ApplicationService with WorkflowService {

  def list: List[WorkflowEntity] = {
    query.searchSummaries
  }

  def show(workflowId: Int): List[WorkflowDetailEntity] = {
    query.searchDefinitions(workflowId)
  }

  def create(entity: WorkflowEntity): Either[Exception, WorkflowEntity] = {
    val nextWorkflowId = query.findMaxSummaryWorkflowId + 1
    summaryRepository.create(entity.copy(workflowId = nextWorkflowId))
  }

  def destroy(entity: WorkflowEntity): Either[Exception, WorkflowEntity] = {
    Try {
      //TODO destroy related entities
      summaryRepository.destroy(entity.id)

    } match {
      case Success(_) => Right(entity)
      case Failure(ex) => Left(new RuntimeException(ex))
    }
  }

  def createDetail(detailEntity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity] = {
    val maybeStatus = workflowStatusRepository.find(detailEntity.status.get.id)

    maybeStatus match {
      case Some(statusEntity) =>
        val entity = WorkflowFactory.buildDefinitionEntity(detailEntity, statusEntity)
        workflowRepository.create(entity)

      case None =>
        Left(new RuntimeException(""))
    }
  }

}
