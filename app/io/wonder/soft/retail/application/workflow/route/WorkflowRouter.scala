package io.wonder.soft.retail.application.workflow.route

import io.wonder.soft.retail.application.workflow.controller.{WorkflowController, WorkflowTransactionController}
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class WorkflowRouter @Inject()
  (workflowController: WorkflowController,
   transactionController: WorkflowTransactionController) extends SimpleRouter {

  val prefix = "workflows"

  override def routes: Routes = {
    case GET(p"/workflows") =>
      workflowController.listSummary

    case GET(p"/workflows/$id") =>
      workflowController.listDefinition(id)

    case POST(p"/workflows/$id") =>
      workflowController.createDefinition(id)

    case POST(p"/workflows") =>
      workflowController.createSummary

    case DELETE(p"/workflows/$id") =>
      workflowController.destroySummary

    case GET(p"/workflows/user-transitions") =>
      transactionController.listTransition

    case POST(p"/workflows/user-transactions") =>
      transactionController.proceedState
  }

}
