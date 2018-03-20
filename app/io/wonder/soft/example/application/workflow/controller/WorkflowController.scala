package io.wonder.soft.example.application.workflow.controller

import javax.inject._

import io.wonder.soft.example.application.helper.JsResultHelper
import io.wonder.soft.example.application.workflow.service.WorkflowService
import io.wonder.soft.example.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowStatusEntity, WorkflowTransitionEntity}
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class WorkflowController @Inject()
  (service: WorkflowService, cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper {

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


  //todo use cats
  //import cats.implicits._
  def createDefinition = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        schemeEntity <- Json.fromJson[WorkflowDefinitionEntity](json).right
        entity <- service.createDefinition(schemeEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(definitionEntity) => Created(Json.toJson(definitionEntity))
        case Left(e) => InternalServerError(JsObject.empty)
      }
      case Failure(_) => InternalServerError(JsObject.empty)
    }
  }

  def listTransition = Action { implicit request =>
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
