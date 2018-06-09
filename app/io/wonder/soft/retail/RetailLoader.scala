package io.wonder.soft.retail

import play.api._
import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.filters.HttpFiltersComponents

class WorkflowLoader extends ApplicationLoader {
  def load(context: Context) = {
    new WorkflowComponents(context).application
  }
}

class WorkflowComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents {

  // set up logger
  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }


  lazy val router = Router.empty
}
