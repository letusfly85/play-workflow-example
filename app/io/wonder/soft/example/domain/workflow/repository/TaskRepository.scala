package io.wonder.soft.example.domain.workflow.repository

import io.wonder.soft.example.domain.Repository
import io.wonder.soft.example.domain.workflow.entity.TaskEntity
import io.wonder.soft.example.domain.workflow.model.Tasks
import scalikejdbc._

import scala.util.{Failure, Success, Try}

object TaskRepository extends Repository[TaskEntity] {

  val tc = Tasks.column

  override def find(id: Int): Option[TaskEntity] = {
    val maybeTasks = Tasks.find(id)
    maybeTasks.flatMap[TaskEntity](schemes => Some(schemes))
  }

  override def create(entity: TaskEntity): Either[Exception, TaskEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(Tasks).namedValues(
            tc.name -> entity.name
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  //TODO
  override def destroy(id: Int): Option[TaskEntity] = None

  //TODO
  override def update(entity: TaskEntity): Either[Exception, TaskEntity] = ???

}
