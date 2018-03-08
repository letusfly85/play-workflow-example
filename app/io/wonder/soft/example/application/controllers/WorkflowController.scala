package io.wonder.soft.example.application.controllers

import javax.inject._

import io.wonder.soft.example.application.services.WorkflowService
import io.wonder.soft.example.domain.workflow.entity.WorkflowStatusEntity
import io.wonder.soft.example.domain.workflow.{WorkflowFactory, WorkflowQueryProcessor}
import play.api.Logger
import play.api.mvc._
import play.api.libs.json._

@Singleton
class WorkflowController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def createStatus = Action { implicit request =>
    request.body.asJson match {
      case Some(json) =>
        Json.fromJson[WorkflowStatusEntity](json) match {
          case JsSuccess(statusEntity, _) =>
            WorkflowService.createStatus(statusEntity) match {
              case Right(_) => Created(Json.toJson(statusEntity))
              case Left(e) =>
                Logger.info(e.toString())
                InternalServerError(JsObject.empty)
            }

          case JsError(e) =>
            Logger.info(e.toString())
            InternalServerError(JsObject.empty)
        }

      case None =>
        InternalServerError(JsObject.empty)
    }
  }

  def updateStatus = Action { implicit request =>
    request.body.asJson match {
      case Some(json) =>
        Json.fromJson[WorkflowStatusEntity](json) match {
          case JsSuccess(statusEntity, _) =>
            WorkflowService.updateStatus(statusEntity) match {
              case Right(_) => Created(Json.toJson(statusEntity))
              case Left(e) =>
                Logger.info(e.toString())
                InternalServerError(JsObject.empty)
            }

          case JsError(e) =>
            Logger.info(e.toString())
            InternalServerError(JsObject.empty)
        }

      case None =>
        InternalServerError(JsObject.empty)
    }
  }

  def statusList = Action {
    val list = WorkflowQueryProcessor.searchStatuses()

    Ok(Json.toJson(list))
  }

  def findScheme(workflowId: String) = Action {
    //TODO search by processor and create a scheme by factory
    val maybeScheme = WorkflowFactory.createSchemeEntity(workflowId.toInt)

    maybeScheme match {
      case Some(schemeEntity) => Ok(Json.toJson(schemeEntity))
      case None => NotFound(JsObject.empty)
    }
  }

}
