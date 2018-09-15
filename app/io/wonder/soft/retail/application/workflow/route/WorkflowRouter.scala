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
      workflowController.list

    case GET(p"/workflows/$id") =>
      workflowController.show(id)

    case POST(p"/workflows") =>
      workflowController.create

    case POST(p"/workflows/$id") =>
      workflowController.update(id)

    case DELETE(p"/workflows/$id") =>
      workflowController.destroy(id)

    case GET(p"/workflows/user-transitions") =>
      transactionController.listTransition

    case POST(p"/workflows/user-transactions") =>
      transactionController.proceedState
  }

}
