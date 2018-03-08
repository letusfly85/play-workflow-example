package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.Repository
import io.wonder.soft.example.domain.workflow.entity.TaskEntity
import io.wonder.soft.example.domain.workflow.model.Tasks

object TaskRepository extends Repository[TaskEntity] {

  override def find(id: Int): Option[TaskEntity] = {
    val maybeSchemes = Tasks.find(id)
    maybeSchemes.flatMap[TaskEntity](schemes => Some(schemes))
  }

  override def create(entity: TaskEntity): Either[Exception, TaskEntity] = ???

  //TODO
  override def destroy(id: Int): Option[TaskEntity] = None

  //TODO
  override def update(entity: TaskEntity): Either[Exception, TaskEntity] = ???

}
