package io.wonder.soft.retail

import io.wonder.soft.retail.application.example.craft.service.CraftLineActionService
import io.wonder.soft.retail.application.workflow.WorkflowServicesModule
import io.wonder.soft.retail.application.workflow.controller.WorkflowController
import io.wonder.soft.retail.application.workflow.service.WorkflowConditionService
import play.api.mvc.ControllerComponents

trait RetailModule extends WorkflowServicesModule {

  import com.softwaremill.macwire._

  lazy val conditionService = wire[WorkflowConditionService]
  lazy val craftLineActionService = wire[CraftLineActionService]
  lazy val workflowController = wire[WorkflowController]

  def controllerComponents: ControllerComponents

}
