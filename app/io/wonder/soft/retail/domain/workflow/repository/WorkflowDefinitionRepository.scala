package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowDefinitionEntity

trait WorkflowDefinitionRepository  {

  def find(id: Int): Option[WorkflowDefinitionEntity]

  def create(entity: WorkflowDefinitionEntity): Either[Exception, WorkflowDefinitionEntity]

  def destroy(id: Int): Option[WorkflowDefinitionEntity]

  def update(entity: WorkflowDefinitionEntity): Either[Exception, WorkflowDefinitionEntity]

}
