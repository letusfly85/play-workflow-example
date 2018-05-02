package io.wonder.soft.retail.domain.example.craft.action

import io.wonder.soft.retail.domain.workflow.service.WorkflowBaseAction

class AssembleAction(_actionId: Int, _workflowId: Int, _transactionId: String, _stepId: Int) extends WorkflowBaseAction {

  val actionId = _actionId

  val workflowId = _workflowId

  val transactionId = _transactionId

  val actionName: String = "組み立てる"

  val stepId = _stepId

}
