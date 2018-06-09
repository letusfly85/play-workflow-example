package io.wonder.soft.retail.application.workflow


import io.wonder.soft.retail.application.workflow.controller.{WorkflowController, WorkflowTransactionController}
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class WorkflowRouter @Inject() (workflowController: WorkflowController, workflowTransactionController: WorkflowTransactionController) extends SimpleRouter {

  val prefix: String = "/api/workflow"

  override def routes: Routes = {
    case GET(p"/workflow-statuses") =>
      workflowController.listStatus

    case POST(p"/workflow-statuses") =>
      workflowController.createStatus

    case PUT(p"/workflow-statuses") =>
      workflowController.updateStatus

    case GET(p"/workflow-summaries") =>
      workflowController.listSummary

    case POST(p"/workflow-summaries") =>
      workflowController.createSummary

    case PUT(p"/workflow-statuses") =>
      workflowController.updateStatus
  }

}
