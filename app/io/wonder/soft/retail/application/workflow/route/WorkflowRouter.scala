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

  val prefix: String = "/api/workflow"

  override def routes: Routes = {
    case GET(p"/summaries") =>
      workflowController.listSummary

    case POST(p"/summaries") =>
      workflowController.createSummary

    case DELETE(p"/summaries") =>
      workflowController.destroySummary

    case GET(p"/definitions") =>
      workflowController.listDefinition

    case GET(p"/definitions/$id") =>
      workflowController.findDefinition(id)

    case POST(p"/definitions") =>
      workflowController.createDefinition

    case GET(p"/transitions") =>
      transitionController.listTransition

    case POST(p"/transitions") =>
      transitionController.createTransition

    case GET(p"/user-transitions") =>
      transactionController.listTransition

    case POST(p"/user-transactions") =>
      transactionController.proceedState
  }

}
