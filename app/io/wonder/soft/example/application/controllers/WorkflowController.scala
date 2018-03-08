package io.wonder.soft.example.application.controllers

import javax.inject._

import io.wonder.soft.example.application.services.WorkflowService
import io.wonder.soft.example.domain.workflow.entity.{WorkflowSchemeEntity, WorkflowStatusEntity}
import play.api.Logger
import play.api.mvc._
import play.api.libs.json._

@Singleton
class WorkflowController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def listStatus = Action {
    Ok(Json.toJson(WorkflowService.listStatus))
  }

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

  def findScheme(id: String) = Action {
    WorkflowService.findScheme(id.toInt) match {
      case Right(schemeEntity) => Ok(Json.toJson(schemeEntity))
      case Left(e) => NotFound(JsObject.empty)
    }
  }

  def listScheme = Action { implicit request =>
    request.getQueryString("workflow-id") match {
      case Some(workflowId) =>
        Ok(Json.toJson(WorkflowService.listScheme(workflowId.toInt)))

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

  def createScheme = Action { implicit request =>
    request.body.asJson match {
      case Some(json) =>
        Json.fromJson[WorkflowSchemeEntity](json) match {
          case JsSuccess(schemeEntity, _) =>
            WorkflowService.createScheme(schemeEntity) match {
              case Right(_) => Created(Json.toJson(schemeEntity))
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

}
