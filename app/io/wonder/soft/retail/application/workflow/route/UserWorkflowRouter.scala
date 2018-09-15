package io.wonder.soft.retail.application.workflow.route

import io.wonder.soft.retail.application.workflow.controller.{WorkflowController, WorkflowTransactionController}
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class UserWorkflowRouter @Inject()
(workflowController: WorkflowController,
 transactionController: WorkflowTransactionController) extends SimpleRouter {

  val prefix = "users"

  override def routes: Routes = {
    case GET(p"/users/${userId}/workflows/${workflowId}/transitions") =>
      transactionController.listTransition

    case POST(p"/users/${userId}/workflows/${workflowId}/transitions") =>
      transactionController.proceedState

  }
}
