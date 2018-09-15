package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDetailEntity, WorkflowEntity}

trait WorkflowService {

  def show(workflowId: Int): List[WorkflowDetailEntity]

  def list: List[WorkflowEntity]

  def create(entity: WorkflowEntity): Either[Exception, WorkflowEntity]

  def destroy(entity: WorkflowEntity): Either[Exception, WorkflowEntity]

  def createDetail(detailEntity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity]

  // def update(entity: WorkflowEntity)


}
