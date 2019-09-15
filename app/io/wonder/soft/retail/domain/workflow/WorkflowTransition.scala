package io.wonder.soft.retail.domain.workflow

case class WorkflowTransition
(
  name: String,
  fromStep: WorkflowStep,
  toStep: WorkflowStep
)

object WorkflowTransition {}
