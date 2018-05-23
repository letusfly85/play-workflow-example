package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowStatusEntity => StatusEntity}
import io.wonder.soft.retail.domain.workflow.query.WorkflowQueryProcessor
import io.wonder.soft.retail.domain.workflow.repository.{WorkflowDefinitionRepository, WorkflowStatusRepository, WorkflowTransitionRepository}
import repository.WorkflowDefinitionSummaryRepository
import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

class WorkflowServiceSpec extends Specification with Mockito {

  val summaryRepository = mock[WorkflowDefinitionSummaryRepository]
  val workflowDefinitionRepository = mock[WorkflowDefinitionRepository]
  val workflowStatusRepository = mock[WorkflowStatusRepository]
  val workflowTransitionRepository = mock[WorkflowTransitionRepository]
  val queryProcessor = mock[WorkflowQueryProcessor]

  // define mock functions
  queryProcessor.searchStatuses() returns List(StatusEntity(1, "test"), StatusEntity(2, "test"), StatusEntity(3, "test"))

  // create a service class
  val service = new WorkflowService(
    summaryRepository = summaryRepository,
    workflowDefinitionRepository = workflowDefinitionRepository,
    workflowStatusRepository = workflowStatusRepository,
    workflowTransitionRepository = workflowTransitionRepository,
    queryProcessor = queryProcessor
  )

  "Workflow Service" should {

    "listStatus" in {
      service.listStatus must haveSize(3)
      service.listStatus.nonEmpty must equalTo(true)
    }
  }
}

object WorkflowServiceProperty extends Properties("WorkflowService") with Mockito {
  val summaryRepository = mock[WorkflowDefinitionSummaryRepository]
  val workflowDefinitionRepository = mock[WorkflowDefinitionRepository]
  val workflowStatusRepository = mock[WorkflowStatusRepository]
  val workflowTransitionRepository = mock[WorkflowTransitionRepository]
  val queryProcessor = mock[WorkflowQueryProcessor]

  // define mock functions
  queryProcessor.searchStatuses() returns List(StatusEntity(1, "test"), StatusEntity(2, "test"), StatusEntity(3, "test"))
  workflowStatusRepository.create(any[StatusEntity]) answers {
    _ match {
      case status: StatusEntity => Right(status)
    }
  }

  // create a service class
  val service = new WorkflowService(
    summaryRepository = summaryRepository,
    workflowDefinitionRepository = workflowDefinitionRepository,
    workflowStatusRepository = workflowStatusRepository,
    workflowTransitionRepository = workflowTransitionRepository,
    queryProcessor = queryProcessor
  )

  property("listStatus") = forAll {
    (_: Int) =>
      service.listStatus.length == 3
      service.listStatus.nonEmpty == true
  }

  property("createStatus") = forAll {
    (statusId: Int, statusName: String) =>
      val either = service.createStatus(StatusEntity(statusId, statusName))
      either.isRight == true
      either.right.get.id == statusId
      either.right.get.name == statusName
  }
}

