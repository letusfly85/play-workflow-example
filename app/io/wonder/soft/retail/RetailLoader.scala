package io.wonder.soft.retail

import io.wonder.soft.retail.application.workflow.WorkflowServicesModule
import play.filters.HttpFiltersComponents

import com.softwaremill.macwire._
import play.api.ApplicationLoader.Context
import play.api._
import play.api.mvc._
import play.api.routing.Router
import router.Routes

class RetailLoader extends ApplicationLoader {
  def load(context: Context) = {
    new RetailComponents(context).application
  }
}

class RetailComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
  with WorkflowServicesModule
  with HttpFiltersComponents {

  // set up logger
  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }


  lazy val router: Router = {
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
}
