package repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowDefinitionSummaryEntity

trait WorkflowDefinitionSummaryRepository {

  def find(id: Int): Option[WorkflowDefinitionSummaryEntity]

  def create(entity: WorkflowDefinitionSummaryEntity): Either[Exception, WorkflowDefinitionSummaryEntity]

  def update(entity: WorkflowDefinitionSummaryEntity): Either[Exception, WorkflowDefinitionSummaryEntity]

  def destroy(id: Int): Option[WorkflowDefinitionSummaryEntity]

}
