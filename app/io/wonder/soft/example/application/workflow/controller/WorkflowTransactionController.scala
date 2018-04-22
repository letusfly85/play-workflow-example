package io.wonder.soft.example.application.workflow.controller

import io.wonder.soft.example.application.helper.JsResultHelper
import io.wonder.soft.example.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.example.domain.workflow.entity.WorkflowUserTransactionEntity
import javax.inject._
import play.api.Logger
import play.api.mvc._
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

@Singleton
class WorkflowTransactionController @Inject()(
    service: WorkflowTransactionService,
    cc: ControllerComponents)
    extends AbstractController(cc)
    with JsResultHelper {

  def listTransition = Action { implicit request =>
    (request.getQueryString("workflow-id"), request.getQueryString("transaction-id")) match {
      case (Some(workflowId), Some(transactionId)) =>
        Ok(Json.toJson(service.listTransition(workflowId.toInt, transactionId)))

      case _ =>
        Logger.info("workflow-id and transanction-id is necessary")
        InternalServerError(JsObject.empty)
    }
  }

  def proceedState = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        transactionEntity <- Json.fromJson[WorkflowUserTransactionEntity](json).right
        entity <- service.proceedState(transactionEntity.transactionId, transactionEntity.transition).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(exception) =>
          Logger.info(exception.toString)
          InternalServerError(JsObject.empty)
      }
      case Failure(exception) =>
        exception.printStackTrace()
        InternalServerError(JsObject.empty)
    }
  }
}
