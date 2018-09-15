package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowDetailEntity

trait WorkflowDetailRepository  {

  def find(id: Int): Option[WorkflowDetailEntity]

  def create(entity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity]

  def destroy(id: Int): Option[WorkflowDetailEntity]

  def update(entity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity]

}
