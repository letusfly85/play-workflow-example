package io.wonder.soft.retail.domain

package workflow {

  case class WorkflowId(value: Int, alias: String) {
    require(value > 0)
  }

  case class WorkflowStatus(id: Int, name: String) {
    require(id > 0)
  }

  trait WorkflowEvent

  case class WorkflowCreated(workflowId: WorkflowId) extends WorkflowEvent

  case class WorkflowStored(workflowId: WorkflowId) extends WorkflowEvent

  case class WorkflowRemoved(workflowId: WorkflowId) extends WorkflowEvent
}
