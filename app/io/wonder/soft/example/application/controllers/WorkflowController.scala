package io.wonder.soft.example.application.controllers

import javax.inject._

import io.wonder.soft.example.domain.workflow.{WorkflowFactory, WorkflowQueryProcessor}
import play.api.mvc._
import play.api.libs.json._

@Singleton
class WorkflowController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def statusList = Action {
    val list = WorkflowQueryProcessor.searchStatuses()

    Ok(Json.toJson(list))
  }

  def findScheme(workflowId: String) = Action {
    //TODO search by processor and create a scheme by factory
    val maybeScheme = WorkflowFactory.createSchemeEntity(workflowId.toInt)

    maybeScheme match {
      case Some(schemeEntity) => Ok(Json.toJson(schemeEntity))
      case None => NotFound(JsObject.empty)
    }
  }

}
