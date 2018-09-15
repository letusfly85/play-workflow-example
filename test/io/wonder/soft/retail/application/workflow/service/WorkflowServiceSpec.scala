package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.application.workflow.service.impl.WorkflowServiceImpl
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowStatusEntity => StatusEntity}
import io.wonder.soft.retail.domain.workflow.query.WorkflowQuery
import io.wonder.soft.retail.domain.workflow.repository._
import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

class WorkflowServiceSpec extends Specification with Mockito {

  val summaryRepository = mock[WorkflowRepository]
  val workflowRepository = mock[WorkflowDetailRepository]
  val workflowStatusRepository = mock[WorkflowStatusRepository]
  val queryProcessor = mock[WorkflowQuery]

  // define mock functions
  queryProcessor.searchStatuses() returns List(StatusEntity(1, "test"), StatusEntity(2, "test"), StatusEntity(3, "test"))

  // create a service class
  val service = new WorkflowServiceImpl(
    summaryRepository = summaryRepository,
    workflowRepository = workflowRepository,
    workflowStatusRepository = workflowStatusRepository,
    query = queryProcessor
  )
}

/*
object WorkflowServiceProperty extends Properties("WorkflowService") with Mockito {
  val summaryRepository = mock[WorkflowRepositoryImpl]
  val workflowRepository = mock[WorkflowDetailRepositoryImpl]
  val workflowStatusRepository = mock[WorkflowStatusRepositoryImpl]
  val queryProcessor = mock[WorkflowQuery]

  queryProcessor.searchStatuses() returns List(StatusEntity(1, "test"), StatusEntity(2, "test"), StatusEntity(3, "test"))
  workflowStatusRepository.create(any[StatusEntity]) answers {
    _ match {
      case status: StatusEntity => Right(status)
    }
  }

  // create a service class
  val service = new WorkflowServiceImpl(
    summaryRepository = summaryRepository,
    workflowRepository = workflowRepository,
    workflowStatusRepository = workflowStatusRepository,
    queryProcessor = queryProcessor
  )

  /*
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
  */
}
*/
