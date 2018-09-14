package io.wonder.soft.retail.application.workflow.route

import io.wonder.soft.retail.application.workflow.controller.{WorkflowController, WorkflowTransactionController, WorkflowTransitionController}
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class WorkflowRouter @Inject()
  (workflowController: WorkflowController,
   transitionController: WorkflowTransitionController,
   transactionController: WorkflowTransactionController) extends SimpleRouter {

  override def routes: Routes = {
    case GET(p"/") =>
      //TODO handle without '/'
      workflowController.listSummary

    case GET(p"/$id") =>
      workflowController.listDefinition(id)

    case POST(p"/$id") =>
      workflowController.createDefinition(id)

    case POST(p"/") =>
      workflowController.createSummary

    case DELETE(p"/$id") =>
      workflowController.destroySummary

    case GET(p"/user-transitions") =>
      transactionController.listTransition

    case POST(p"/user-transactions") =>
      transactionController.proceedState
  }

}
