package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowActionConditionEntity

trait WorkflowActionConditionRepository {

  def find(id: Int): Option[WorkflowActionConditionEntity]

  def create(entity: WorkflowActionConditionEntity): Either[Exception, WorkflowActionConditionEntity]

  def update(entity: WorkflowActionConditionEntity): Either[RuntimeException, WorkflowActionConditionEntity]

  def destroy(id: Int): Option[WorkflowActionConditionEntity]

}
