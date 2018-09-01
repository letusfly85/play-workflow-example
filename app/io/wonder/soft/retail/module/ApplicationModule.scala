package io.wonder.soft.retail.module

import com.google.inject.AbstractModule
import io.wonder.soft.retail.application.workflow.service.{WorkflowService, WorkflowTransactionService, WorkflowTransitionService}
import io.wonder.soft.retail.application.workflow.service.impl.{WorkflowServiceImpl, WorkflowTransactionServiceImpl, WorkflowTransitionServiceImpl}
import net.codingwell.scalaguice.ScalaModule

class ApplicationModule extends AbstractModule with ScalaModule {

  override def configure() = {
    bind[WorkflowService].to[WorkflowServiceImpl]
    bind[WorkflowTransitionService].to[WorkflowTransitionServiceImpl]
    bind[WorkflowTransactionService].to[WorkflowTransactionServiceImpl]
  }

}
