package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDetailEntity, WorkflowEntity}

trait WorkflowService {

  def list: List[WorkflowEntity]

  def find(workflowId: Int): Option[WorkflowEntity]

  def create(entity: WorkflowEntity): Either[Exception, WorkflowEntity]

  def destroy(entity: WorkflowEntity): Either[Exception, WorkflowEntity]

  def update(entity: WorkflowEntity): Either[Exception, WorkflowEntity]


}
