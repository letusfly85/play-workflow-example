package io.wonder.soft.retail.domain.workflow

final case class Workflow
(
  workflowId: WorkflowId,
  name: String,
  description: Option[String],
  steps: List[WorkflowStep]
)

object Workflow {

  def create(workflowId: WorkflowId, name: String, description: Option[String], steps: List[WorkflowStep]): (Workflow, WorkflowEvent) = {
    val domainObject =
      Workflow(
        workflowId,
        name,
        description,
        steps
      )

    val domainEvent = WorkflowCreated(workflowId)

    (domainObject, domainEvent)
  }

}
