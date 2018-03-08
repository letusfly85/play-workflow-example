package io.wonder.soft.example.application.controllers

import javax.inject._

import io.wonder.soft.example.domain.workflow.WorkflowQueryProcessor
import play.api.mvc._
import play.api.libs.json._

@Singleton
class WorkflowController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def statusList = Action {
    val list = WorkflowQueryProcessor.searchStatuses()

    Ok(Json.toJson(list))
  }

}
