package io.wonder.soft.retail.application.workflow.controller

import io.wonder.soft.retail.application.helper.JsResultHelper
import io.wonder.soft.retail.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.retail.domain.workflow.entity.WorkflowUserTransactionEntity
import javax.inject._
import play.api.Logger
import play.api.mvc._
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

@Singleton
class UserTransactionController @Inject()(
    service: WorkflowTransactionService,
    cc: ControllerComponents)
    extends AbstractController(cc)
    with JsResultHelper {

  def findTransition(userId: String, workflowId: String, transactionId: String) = Action { implicit request =>
    Ok(Json.toJson(service.listTransition(workflowId.toInt, transactionId)))
  }

  def proceed(userId: String, workflowId: String, transactionId: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        transactionEntity <- Json.fromJson[WorkflowUserTransactionEntity](json).right
        entity <- service.proceedState(transactionEntity.transactionId, transactionEntity.transition).right
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
