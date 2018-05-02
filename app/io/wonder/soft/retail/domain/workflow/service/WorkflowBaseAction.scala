package io.wonder.soft.retail.domain.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.ActionTransactionEntity

trait WorkflowBaseAction {
  val actionId: Int

  val actionName: String

  val workflowId: Int

  val transactionId: String

  val stepId: Int

  def saveActionTransaction(transactionId: String, actionId: Int): Either[Exception, ActionTransactionEntity] = {
    ???
  }

  def revertActionTransaction(transactionId: String, actionId: Int): Either[Exception, ActionTransactionEntity] = {
    ???
  }
}
