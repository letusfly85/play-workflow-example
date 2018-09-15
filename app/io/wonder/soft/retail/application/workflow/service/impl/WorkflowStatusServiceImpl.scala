package io.wonder.soft.retail.application.workflow.service.impl

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowStatusService
import io.wonder.soft.retail.domain.workflow.entity.WorkflowStatusEntity
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import io.wonder.soft.retail.domain.workflow.repository.WorkflowStatusRepository
import javax.inject.Inject

class WorkflowStatusServiceImpl @Inject()
(workflowStatusRepository: WorkflowStatusRepository,
 queryProcessor: WorkflowQuery
)
  extends ApplicationService with WorkflowStatusService {

  def list: List[WorkflowStatusEntity] = queryProcessor.searchStatuses

  def create(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
    workflowStatusRepository.create(workflowStatusEntity)

  def update(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity] =
    workflowStatusRepository.update(workflowStatusEntity)


}
