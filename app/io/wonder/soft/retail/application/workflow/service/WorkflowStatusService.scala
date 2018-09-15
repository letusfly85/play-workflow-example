package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.WorkflowStatusEntity

trait WorkflowStatusService {

  def list: List[WorkflowStatusEntity]

  def create(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

  def update(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

}
