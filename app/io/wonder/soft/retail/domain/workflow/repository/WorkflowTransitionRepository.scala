package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowTransitionEntity

trait WorkflowTransitionRepository  {

  def find(id: Int): Option[WorkflowTransitionEntity]

  def create(entity: WorkflowTransitionEntity): Either[Exception, WorkflowTransitionEntity]

  def update(entity: WorkflowTransitionEntity): Either[RuntimeException, WorkflowTransitionEntity]

  def destroy(id: Int): Option[WorkflowTransitionEntity]

}
