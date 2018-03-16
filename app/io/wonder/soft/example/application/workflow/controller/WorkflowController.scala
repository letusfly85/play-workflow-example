package io.wonder.soft.example.application.workflow.controller

import javax.inject._

import io.wonder.soft.example.application.workflow.service.WorkflowService
import io.wonder.soft.example.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowStatusEntity, WorkflowTransitionEntity}
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._

@Singleton
class WorkflowController @Inject()(service: WorkflowService, cc: ControllerComponents) extends AbstractController(cc) {

  def listStatus = Action {
    Ok(Json.toJson(service.listStatus))
  }

  def createStatus = Action { implicit request =>
    request.body.asJson match {
      case Some(json) =>
        Json.fromJson[WorkflowStatusEntity](json) match {
          case JsSuccess(statusEntity, _) =>
            service.createStatus(statusEntity) match {
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

  def findDefinition(id: String) = Action {
    service.findDefinition(id.toInt) match {
      case Right(schemeEntity) => Ok(Json.toJson(schemeEntity))
      case Left(e) => NotFound(JsObject.empty)
    }
  }

  def listDefinition = Action { implicit request =>
    request.getQueryString("workflow-id") match {
      case Some(workflowId) =>
        Ok(Json.toJson(service.listDefinition(workflowId.toInt)))

      case None =>
        InternalServerError(JsObject.empty)
    }
  }

  def updateStatus = Action { implicit request =>
    request.body.asJson match {
      case Some(json) =>
        Json.fromJson[WorkflowStatusEntity](json) match {
          case JsSuccess(statusEntity, _) =>
            service.updateStatus(statusEntity) match {
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

  def createDefinition = Action { implicit request =>
    request.body.asJson match {
      case Some(json) =>
        Json.fromJson[WorkflowDefinitionEntity](json) match {
          case JsSuccess(schemeEntity, _) =>
            service.createDefinition(schemeEntity) match {
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

  def listTransition(workflowId: String) = Action { implicit request =>
    request.getQueryString("workflow-id") match {
      case Some(workflowId) =>
        Ok(Json.toJson(service.listTransition(workflowId.toInt)))

      case None =>
        InternalServerError(JsObject.empty)
    }
  }

  def createTransition = Action { implicit request =>
    request.body.asJson match {
      case Some(json) =>
        Json.fromJson[WorkflowTransitionEntity](json) match {
          case JsSuccess(transitionEntity, _) =>
            service.createTransition(transitionEntity) match {
              case Right(_) => Created(Json.toJson(transitionEntity))
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
