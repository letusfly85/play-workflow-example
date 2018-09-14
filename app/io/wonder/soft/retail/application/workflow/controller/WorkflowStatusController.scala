package io.wonder.soft.retail.application.workflow.controller

import io.wonder.soft.retail.application.helper.JsResultHelper
import io.wonder.soft.retail.application.workflow.service.WorkflowService
import io.wonder.soft.retail.domain.workflow.entity._
import javax.inject._
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class WorkflowStatusController @Inject()
  (service: WorkflowService,
   cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper {

  def listStatus = Action {
    Ok(Json.toJson(service.listStatus))
  }

  def createStatus = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        statusEntity <- Json.fromJson[WorkflowStatusEntity](json).right
        entity <- service.createStatus(statusEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(e) => InternalServerError(JsObject.empty)
      }
      case Failure(_) => InternalServerError(JsObject.empty)
    }
  }

  def updateStatus = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        statusEntity <- Json.fromJson[WorkflowStatusEntity](json).right
        entity <- service.updateStatus(statusEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(e) => InternalServerError(JsObject.empty)
      }
      case Failure(_) => InternalServerError(JsObject.empty)
    }
  }
}
