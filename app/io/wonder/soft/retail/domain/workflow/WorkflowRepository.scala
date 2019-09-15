package io.wonder.soft.retail.domain.workflow

class WorkflowRepository {

  def find(workflowId: WorkflowId): Option[Workflow] = {
    None
  }

  def store(workflow: Workflow): Either[Exception, Workflow] = {
    Right(workflow)
  }

  def remove(workflow: Workflow): Option[Workflow] = {
    None
  }

}
