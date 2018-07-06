package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowTransactionEntity

trait WorkflowTransactionRepository {

  def find(id: Int): Option[WorkflowTransactionEntity]

  def create(entity: WorkflowTransactionEntity): Either[Exception, WorkflowTransactionEntity]

  def update(entity: WorkflowTransactionEntity): Either[RuntimeException, WorkflowTransactionEntity]

  def destroy(id: Int): Option[WorkflowTransactionEntity]

}
