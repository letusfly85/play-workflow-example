package io.wonder.soft.retail.application.workflow


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
    case GET(p"/statuses") =>
      workflowController.listStatus

    case POST(p"/statuses") =>
      workflowController.createStatus

    case PUT(p"/statuses") =>
      workflowController.updateStatus

    case GET(p"/summaries") =>
      workflowController.listSummary

    case POST(p"/summaries") =>
      workflowController.createSummary

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
