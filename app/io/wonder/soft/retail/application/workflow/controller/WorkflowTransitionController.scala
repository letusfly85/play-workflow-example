package io.wonder.soft.retail.application.workflow.controller

import io.wonder.soft.retail.application.helper.JsResultHelper
import io.wonder.soft.retail.application.workflow.service.WorkflowTransitionService
import io.wonder.soft.retail.domain.workflow.entity._
import javax.inject._
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class WorkflowTransitionController @Inject()
  (transitionService: WorkflowTransitionService,
   cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper {

  def listTransition(workflowId: String) = Action { implicit request =>
     Ok(Json.toJson(transitionService.listTransition(workflowId.toInt)))
  }

  def createTransition(workflowId: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        transitionEntity <- Json.fromJson[WorkflowTransitionEntity](json).right
        entity <- transitionService.createTransition(transitionEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(transitionEntity) => Created(Json.toJson(transitionEntity))
        case Left(e) => InternalServerError(JsObject.empty)
      }
      case Failure(_) => InternalServerError(JsObject.empty)
    }
  }

  // TODO
  def listOrderActions = ???

  def createActionCondition = ???

  def deleteActionCondition = ???

}
