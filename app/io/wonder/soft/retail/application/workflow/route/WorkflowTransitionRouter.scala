package io.wonder.soft.retail.application.workflow.route

import io.wonder.soft.retail.application.workflow.controller.WorkflowTransitionController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class WorkflowTransitionRouter @Inject()(transitionController: WorkflowTransitionController) extends SimpleRouter {

  val prefix: String = "/api/workflows"

  override def routes: Routes = {
    case GET(p"/workflows/${workflowId}/transitions") =>
      transitionController.list(workflowId)

    case POST(p"/workflows/${workflowId}/transitions") =>
      transitionController.create(workflowId)

    case DELETE(p"/workflows/${workflowId}/transitions/${transitionId}") =>
      transitionController.delete(workflowId, transitionId)
  }
}
