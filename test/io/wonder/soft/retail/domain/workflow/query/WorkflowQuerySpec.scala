package io.wonder.soft.retail.domain.workflow.query

import io.wonder.soft.retail.domain.workflow.model.{WorkflowDetails, WorkflowStatuses}
import org.specs2.mock.Mockito
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.specs2.mutable.{BeforeAfter, Specification}
import scalikejdbc._
import scalikejdbc.config.DBs

class WorkflowQuerySpec extends Specification with Mockito {
  DBs.setupAll()

  val query = new WorkflowQuery

  trait AfterScope extends BeforeAfter {
    val testWorkflowId = 99
    val testWorkflowName = "test"

    def before: Any = {
      DB localTx { implicit session =>
        SQL("insert into workflow_details (id, workflow_id, name, status_id, step_id, step_label, is_first_step, is_last_step) values (?, ?, ?, ?, ?, ?, ?, ?)")
          .bind(100, testWorkflowId, testWorkflowName, 99, 1, "step1", true, false).update.apply
        SQL("insert into workflow_details (id, workflow_id, name, status_id, step_id, step_label, is_first_step, is_last_step) values (?, ?, ?, ?, ?, ?, ?, ?)")
          .bind(101, testWorkflowId, testWorkflowName, 100, 2, "step1", false, false).update.apply
        SQL("insert into workflow_details (id, workflow_id, name, status_id, step_id, step_label, is_first_step, is_last_step) values (?, ?, ?, ?, ?, ?, ?, ?)")
          .bind(102, testWorkflowId, testWorkflowName, 100, 3, "step1", false, false).update.apply
        SQL("insert into workflow_details (id, workflow_id, name, status_id, step_id, step_label, is_first_step, is_last_step) values (?, ?, ?, ?, ?, ?, ?, ?)")
          .bind(103, testWorkflowId, testWorkflowName, 100, 4, "step1", false, false).update.apply
        SQL("insert into workflow_details (id, workflow_id, name, status_id, step_id, step_label, is_first_step, is_last_step) values (?, ?, ?, ?, ?, ?, ?, ?)")
          .bind(104, testWorkflowId, testWorkflowName, 101, 5, "step1", false, true).update.apply


        SQL("insert into workflow_statuses (id, name) values (?, ?)").bind(99, "not running").update.apply
        SQL("insert into workflow_statuses (id, name) values (?, ?)").bind(100, "progressing").update.apply
        SQL("insert into workflow_statuses (id, name) values (?, ?)").bind(101, "done").update.apply
      }
    }

    override def after: Any = {
      WorkflowDetails.findAllBy(
        sqls.eq(WorkflowDetails.column.workflowId, testWorkflowId)
      ).foreach(_.destroy())

      List(99, 100, 101).foreach {id =>
        WorkflowStatuses.find(id).map(_.destroy())
      }
    }
  }

  /*
  "WorkflowQueryProcessor" should {

    "searchDefinitionsByDefinitionId" in new  AfterScope {
      val existDefinitionId = 100
      val maybeDefinition = query.searchDefinitionsByDefinitionId(existDefinitionId)

      maybeDefinition.isDefined must beTrue

      //TODO implement value check
    }

  }
  */

}
