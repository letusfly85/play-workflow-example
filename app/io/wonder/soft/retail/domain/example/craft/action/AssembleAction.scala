package io.wonder.soft.retail.domain.example.craft.action

class AssembleAction extends CraftBaseAction {

  val actionName: String = ""

  def saveActionTransaction(transactionId: String, actionId: Int) = ???

  def revertActionTransaction(transactionId: String, actionId: Int) = ???

}
