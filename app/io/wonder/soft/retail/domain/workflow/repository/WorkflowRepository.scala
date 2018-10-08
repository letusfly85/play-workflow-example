package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowEntity

trait WorkflowRepository {

  def find(id: Int): Option[WorkflowEntity]

  def save(entity: WorkflowEntity): Either[Exception, WorkflowEntity]

  def destroy(id: Int): Option[WorkflowEntity]

}
