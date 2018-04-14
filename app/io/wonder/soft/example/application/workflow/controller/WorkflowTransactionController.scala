package io.wonder.soft.example.application.workflow.controller

import io.wonder.soft.example.application.helper.JsResultHelper
import io.wonder.soft.example.application.workflow.service.WorkflowTransactionService
import javax.inject._
import play.api.Logger
import play.api.mvc._
import play.api.libs.json._

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
}
