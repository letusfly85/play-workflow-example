package io.wonder.soft.retail.domain.example.craft.action

import io.wonder.soft.retail.domain.workflow.entity.ActionTransactionEntity

trait CraftBaseAction {

  val actionName: String

  def saveActionTransaction(transactionId: String, actionId: Int): Either[Exception, ActionTransactionEntity]

  def revertActionTransaction(transactionId: String, actionId: Int): Either[Exception, ActionTransactionEntity]

}
