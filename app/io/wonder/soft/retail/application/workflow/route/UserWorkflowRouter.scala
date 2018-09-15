package io.wonder.soft.retail.application.workflow.route

import io.wonder.soft.retail.application.workflow.controller.UserTransactionController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class UserWorkflowRouter @Inject() (userTransaction: UserTransactionController) extends SimpleRouter {

  val prefix = "users"

  override def routes: Routes = {
    case GET(p"/users/${userId}/workflows/${workflowId}/transactions/${transactionId}/transitions") =>
      userTransaction.findTransition(userId, workflowId, transactionId)

    case POST(p"/users/${userId}/workflows/${workflowId}/transactions/${transactionId}") =>
      userTransaction.proceed(userId, workflowId, transactionId)
  }

}
