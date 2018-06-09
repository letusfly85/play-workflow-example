package io.wonder.soft.retail

import play.filters.HttpFiltersComponents
import com.softwaremill.macwire._
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
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
}
