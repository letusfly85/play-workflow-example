package io.wonder.soft.example.application.workflow.controller

import io.wonder.soft.example.application.helper.JsResultHelper
import io.wonder.soft.example.application.workflow.service.WorkflowTransactionService
import javax.inject._
import play.api.mvc._

@Singleton
class WorkflowTransactionController @Inject()(
    service: WorkflowTransactionService,
    cc: ControllerComponents)
    extends AbstractController(cc)
    with JsResultHelper {}
