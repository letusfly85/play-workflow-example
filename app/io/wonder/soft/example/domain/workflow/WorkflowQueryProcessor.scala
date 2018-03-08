package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity._
import io.wonder.soft.example.domain.workflow.model.WorkflowStatuses

object WorkflowQueryProcessor {
  import WorkflowStatusEntity._

  def searchStatuses(): List[WorkflowStatusEntity] = {
    WorkflowStatuses.findAll()
  }

}
