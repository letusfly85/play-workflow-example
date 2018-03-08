package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.TaskEntity
import io.wonder.soft.example.domain.workflow.model.Tasks

object TaskQueryProcessor {

  def searchTasks: List[TaskEntity] = {
    Tasks.findAll()
  }

}
