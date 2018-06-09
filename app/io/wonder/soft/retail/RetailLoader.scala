package io.wonder.soft.retail

import play.filters.HttpFiltersComponents
import com.softwaremill.macwire._
import io.wonder.soft.retail.application.workflow.WorkflowRouter
import play.api.ApplicationLoader.Context
import play.api._
import play.api.mvc._
import play.api.routing.Router
import router.Routes
import scalikejdbc.config.DBs

class RetailLoader extends ApplicationLoader {
  def load(context: Context) = {
    new RetailComponents(context).application
  }
}

class RetailComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
  with RetailModule
  with HttpFiltersComponents {

  // set up logger
  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  DBs.setupAll()

  lazy val router: Router = {
    val prefix: String = "/"

    lazy val workflowRouter: WorkflowRouter = wire[WorkflowRouter]
    lazy val wiredRouter: Routes = wire[Routes]

    lazy val router = wiredRouter.withPrefix(prefix)

    router
  }
}
