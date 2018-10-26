package io.wonder.soft.retail.application.workflow.controller

import io.wonder.soft.retail.application.helper.JsResultHelper
import io.wonder.soft.retail.application.workflow.service.WorkflowTransitionService
import io.wonder.soft.retail.domain.workflow.entity._
import javax.inject._
import play.api.libs.json._
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class WorkflowTransitionController @Inject()
  (transitionService: WorkflowTransitionService,
   cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper {

  def list(workflowId: String) = Action { implicit request =>
     Ok(Json.toJson(transitionService.listTransition(workflowId.toInt)))
  }

  def create(workflowId: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        transitionEntity <- Json.fromJson[WorkflowTransitionEntity](json).right
        entity <- transitionService.createTransition(transitionEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(transitionEntity) => Created(Json.toJson(transitionEntity))
        case Left(e) => errorHandler(e)
      }
      case Failure(e) => errorHandler(new Exception(e))
    }
  }

  def delete(workflowId: String, transitionId: String) = Action { implicit request =>
    Try {
        transitionService.destroyTransition(workflowId.toInt, transitionId.toInt)
    } match {
      case Success(either) => either match {
        case Right(transitionEntity) => Ok(Json.toJson(transitionEntity))
        case Left(e) => errorHandler(e)
      }
      case Failure(e) => errorHandler(new Exception(e))
    }
  }
}
