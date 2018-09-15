package io.wonder.soft.retail.module

import com.google.inject.AbstractModule
import io.wonder.soft.retail.domain.example.craft.repository.CraftLineRepository
import io.wonder.soft.retail.domain.example.craft.repository.impl.CraftLineRepositoryImpl
import io.wonder.soft.retail.domain.example.order.repository.OrderRepository
import io.wonder.soft.retail.domain.example.order.repository.impl.OrderRepositoryImpl
import io.wonder.soft.retail.domain.workflow.repository._
import net.codingwell.scalaguice.ScalaModule

class DomainModule extends AbstractModule with ScalaModule {

  def configure = {

    bind[WorkflowDetailRepository].to[WorkflowDetailRepositoryImpl]
    bind[WorkflowStatusRepository].to[WorkflowStatusRepositoryImpl]

    bind[CraftLineRepository].to[CraftLineRepositoryImpl]
    bind[OrderRepository].to[OrderRepositoryImpl]
    bind[WorkflowTransitionRepository].to[WorkflowTransitionRepositoryImpl]
  }

}
