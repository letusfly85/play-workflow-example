import com.google.inject.AbstractModule
import java.time.Clock

import io.wonder.soft.example.application.workflow.service.WorkflowService
import io.wonder.soft.example.domain.workflow.entity.WorkflowDefinitionEntity
import io.wonder.soft.example.domain.workflow.repository.{WorkflowDefinitionRepository, WorkflowStatusRepository}

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[WorkflowDefinitionRepository])
    bind(classOf[WorkflowStatusRepository])
    bind(classOf[WorkflowService])

    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
  }

}
