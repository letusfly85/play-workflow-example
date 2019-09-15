package io.wonder.soft.retail.domain

package workflow {

  case class WorkflowId(value: Int, alias: String) {
    require(value > 0)
  }

  case class WorkflowStatus(id: Int, name: String) {
    require(id > 0)
  }

}
