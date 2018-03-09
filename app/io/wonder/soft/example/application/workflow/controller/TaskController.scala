package io.wonder.soft.example.application.workflow.controller

import javax.inject._

import io.wonder.soft.example.application.workflow.service.TaskService
import io.wonder.soft.example.domain.workflow.entity.TaskEntity
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._

@Singleton
class TaskController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def listTask = Action {
    Ok(Json.toJson(TaskService.searchTasks))
  }

  def createTask = Action { implicit request =>
    request.body.asJson match {
      case Some(json) =>
        Json.fromJson[TaskEntity](json) match {
          case JsSuccess(entity, _) =>
            TaskService.createTask(entity) match {
              case Right(_) => Created(Json.toJson(entity))
              case Left(e) => InternalServerError(JsObject.empty)
            }

          case JsError(e) =>
            Logger.info(e.toString())
            BadRequest(JsObject.empty)
        }

      case None =>
        BadRequest(JsObject.empty)
    }
  }

}
