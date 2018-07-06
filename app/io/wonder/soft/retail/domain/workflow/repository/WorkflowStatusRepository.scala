package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowStatusEntity

trait WorkflowStatusRepository  {

  def find(id: Int): Option[WorkflowStatusEntity]

  def create(entity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

  def update(entity: WorkflowStatusEntity): Either[RuntimeException, WorkflowStatusEntity]

  def destroy(id: Int): Option[WorkflowStatusEntity]
}
