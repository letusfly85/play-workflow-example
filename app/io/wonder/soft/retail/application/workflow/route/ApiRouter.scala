package io.wonder.soft.retail.application.workflow.route

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter

class ApiRouter @Inject()
 (workflowRouter: WorkflowRouter,
  workflowStatusRouter: WorkflowStatusRouter,
  workflowTransitionRouter: WorkflowTransitionRouter
 ) extends SimpleRouter {

  override def routes: Routes = {
    workflowRouter.routes orElse
    workflowStatusRouter.routes orElse
    workflowTransitionRouter.routes
  }

}
