package io.wonder.soft.retail.domain.workflow

final case class Workflow
(
  workflowId: WorkflowId,
  name: String,
  description: Option[String],
  steps: List[WorkflowStep]
)

object Workflow {}
