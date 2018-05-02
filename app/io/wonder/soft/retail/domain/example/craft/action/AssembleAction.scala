package io.wonder.soft.retail.domain.example.craft.action

class AssembleAction(_actionId: Int, _workflowId: Int, _transactionId: String, _stepId: Int) {

  val actionId = _actionId

  val workflowId = _workflowId

  val transactionId = _transactionId

  val actionName: String = "組み立てる"

  val stepId = _stepId

}
