package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowCurrentStateEntity

trait WorkflowCurrentStateRepository {

  def find(id: Int): Option[WorkflowCurrentStateEntity]

  def create(entity: WorkflowCurrentStateEntity): Either[Exception, WorkflowCurrentStateEntity]

  def update(entity: WorkflowCurrentStateEntity): Either[RuntimeException, WorkflowCurrentStateEntity]

  def destroy(id: Int): Option[WorkflowCurrentStateEntity]

}
