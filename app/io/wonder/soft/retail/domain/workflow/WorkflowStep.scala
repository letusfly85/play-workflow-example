package io.wonder.soft.retail.domain.workflow

final case class WorkflowStep
(
  name: String,
  status: WorkflowStatus,
  stepId: Int,
  stepLabel: String,
  isFirstStep: Boolean = false,
  isLastStep: Boolean = false
) {
  require(stepId > 0)
}

object WorkflowStep {}
