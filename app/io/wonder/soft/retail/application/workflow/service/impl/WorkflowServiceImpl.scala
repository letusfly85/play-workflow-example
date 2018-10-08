package io.wonder.soft.retail.application.workflow.service.impl

import javax.inject.Inject
import io.wonder.soft.retail.domain.workflow.entity.WorkflowEntity
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import io.wonder.soft.retail.domain.workflow.repository.{WorkflowDetailRepository, WorkflowRepository, WorkflowStatusRepository}
import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowService

import scala.util.{Failure, Success, Try}

class WorkflowServiceImpl @Inject()
(
  repository: WorkflowRepository,
  detailRepository: WorkflowDetailRepository,
  statusRepository: WorkflowStatusRepository,
  query: WorkflowQuery
) extends ApplicationService with WorkflowService {

  def list: List[WorkflowEntity] = query.list

  def find(workflowId: Int): Option[WorkflowEntity] = query.find(workflowId)

  def create(entity: WorkflowEntity): Either[Exception, WorkflowEntity] = {
    val nextWorkflowId = query.findMaxSummaryWorkflowId + 1
    repository.save(entity.copy(workflowId = nextWorkflowId))
  }

  def update(entity: WorkflowEntity): Either[Exception, WorkflowEntity] = {
    repository.save(entity)
  }

  def destroy(entity: WorkflowEntity): Either[Exception, WorkflowEntity] = {
    Try {
      //TODO destroy related entities
      repository.destroy(entity.id)

    } match {
      case Success(_) => Right(entity)
      case Failure(ex) => Left(new RuntimeException(ex))
    }
  }
}
