package io.wonder.soft.example.application.workflow.service

import io.wonder.soft.example.application.ApplicationService
import io.wonder.soft.example.domain.workflow.entity.TaskEntity
import io.wonder.soft.example.domain.workflow.query.TaskQueryProcessor
import io.wonder.soft.example.domain.workflow.repository.TaskRepository

object TaskService extends ApplicationService {

  def searchTasks: List[TaskEntity] = TaskQueryProcessor.searchTasks

  def createTask(taskEntity: TaskEntity): Either[Exception, TaskEntity] = {
    TaskRepository.create(taskEntity)
  }

}
