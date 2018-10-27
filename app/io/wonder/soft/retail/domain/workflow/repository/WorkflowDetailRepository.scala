package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowStepEntity

trait WorkflowDetailRepository  {

  def find(id: Int): Option[WorkflowStepEntity]

  def create(entity: WorkflowStepEntity): Either[Exception, WorkflowStepEntity]

  def destroy(id: Int): Option[WorkflowStepEntity]

  def update(entity: WorkflowStepEntity): Either[Exception, WorkflowStepEntity]

}
