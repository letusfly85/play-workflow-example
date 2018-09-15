package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.application.workflow.service.impl.WorkflowStatusServiceImpl
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowStatusEntity => StatusEntity}
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import io.wonder.soft.retail.domain.workflow.repository._
import org.specs2.mutable.Specification
import org.specs2.mock.Mockito

class WorkflowStatusServiceSpec extends Specification with Mockito {

  val workflowStatusRepository = mock[WorkflowStatusRepository]
  val queryProcessor = mock[WorkflowQuery]

  // define mock functions
  queryProcessor.searchStatuses() returns List(StatusEntity(1, "test"), StatusEntity(2, "test"), StatusEntity(3, "test"))

  // create a service class
  val service = new WorkflowStatusServiceImpl(
    workflowStatusRepository = workflowStatusRepository,
    queryProcessor = queryProcessor
  )

  "Workflow Service" should {

    "listStatus" in {
      service.list must haveSize(3)
      service.list.nonEmpty must equalTo(true)
    }
  }
}

