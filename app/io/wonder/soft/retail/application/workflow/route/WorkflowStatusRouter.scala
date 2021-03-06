package io.wonder.soft.retail.application.workflow.route

import io.wonder.soft.retail.application.workflow.controller.WorkflowStatusController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class WorkflowStatusRouter @Inject() (statusController: WorkflowStatusController) extends SimpleRouter {

  val prefix: String = "/api/workflow-statuses"

  override def routes: Routes = {
    case GET(p"/workflow-statuses") =>
      statusController.listStatus

    case POST(p"/workflow-statuses") =>
      statusController.createStatus

    case PUT(p"/workflow-statuses") =>
      statusController.updateStatus
  }
}
