package io.wonder.soft.retail.application.workflow.controller

import io.wonder.soft.retail.application.helper.JsResultHelper
import io.wonder.soft.retail.application.workflow.service.WorkflowStatusService
import io.wonder.soft.retail.domain.workflow.entity._
import javax.inject._
import play.api.libs.json._
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class WorkflowStatusController @Inject()
  (service: WorkflowStatusService,
   cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper {

  def listStatus = Action {
    Ok(Json.toJson(service.list))
  }

  def createStatus = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        statusEntity <- Json.fromJson[WorkflowStatusEntity](json).right
        entity <- service.create(statusEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(e) => errorHandler(e)
      }
      case Failure(e) => errorHandler(new Exception(e))
    }
  }

  def updateStatus = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        statusEntity <- Json.fromJson[WorkflowStatusEntity](json).right
        entity <- service.update(statusEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(e) => errorHandler(e)
      }
      case Failure(e) => errorHandler(new Exception(e))
    }
  }
}
