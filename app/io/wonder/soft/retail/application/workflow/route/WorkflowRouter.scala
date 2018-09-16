package io.wonder.soft.retail.application.workflow.route

import io.wonder.soft.retail.application.workflow.controller.{WorkflowController, UserTransactionController}
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class WorkflowRouter @Inject()
  (workflowController: WorkflowController,
   transactionController: UserTransactionController) extends SimpleRouter {

  val prefix = "workflows"

  override def routes: Routes = {
    case GET(p"/workflows") =>
      workflowController.list

    case GET(p"/workflows/${id}") =>
      workflowController.search(id)

    case GET(p"/workflows/$id") =>
      workflowController.show(id)

    case POST(p"/workflows") =>
      workflowController.create

    case POST(p"/workflows/$id") =>
      workflowController.update(id)

    case DELETE(p"/workflows/$id") =>
      workflowController.destroy(id)
  }

}
