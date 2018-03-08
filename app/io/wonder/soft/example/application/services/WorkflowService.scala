package io.wonder.soft.example.application.services

import io.wonder.soft.example.domain.workflow.WorkflowStatusRepository
import io.wonder.soft.example.domain.workflow.entity.WorkflowStatusEntity

object WorkflowService {

  def createStatus(workflowStatus: WorkflowStatusEntity): Either[Throwable, WorkflowStatusEntity] = {
    try {
      WorkflowStatusRepository.create(workflowStatus)

    } catch {
      case e: Exception => Left(e)
    }
  }

}
