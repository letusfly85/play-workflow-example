package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.specs2.specification.BeforeAfterAll
import scalikejdbc.config.DBs


class WorkflowStepsSpec extends Specification with BeforeAfterAll {
  DBs.setupAll()

  def beforeAll = {
    val testWorkflowId = 99
    val testWorkflowName = "test"
    DB localTx { implicit session =>
      SQL("delete from workflow_statuses where id = ?").bind(1).update.apply
      SQL("insert into workflow_statuses (id, name) values (?, ?)").bind(1, "未着手").update.apply
      SQL("delete from workflow_steps where id = ?").bind(123).update.apply
      SQL("insert into workflow_steps (id, workflow_id, name, status_id, step_id, step_label, is_first_step, is_last_step) values (?, ?, ?, ?, ?, ?, ?, ?)")
        .bind(123, testWorkflowId, testWorkflowName, 99, 1, "step1", true, false).update.apply
    }
  }

  def afterAll = {
    DB localTx { implicit session =>
      SQL("delete from workflow_steps where id = ?").bind(123).update.apply
    }
  }

  "WorkflowSteps" should {

    val wd = WorkflowSteps.syntax("wd")

    "find by primary keys" in new AutoRollback {
      val maybeFound = WorkflowSteps.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = WorkflowSteps.findBy(sqls.eq(wd.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = WorkflowSteps.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = WorkflowSteps.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = WorkflowSteps.findAllBy(sqls.eq(wd.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = WorkflowSteps.countBy(sqls.eq(wd.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created =
        WorkflowSteps.create(
          workflowId = 123,
          name = "MyString",
          statusId = 1,
          stepId = 123,
          stepLabel = "MyString",
          isFirstStep = false,
          isLastStep = false)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = WorkflowSteps.find(123).get
      val modified = entity.copy(name = "hoge")
      val updated = WorkflowSteps.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = WorkflowSteps.find(123).get
      val deleted = WorkflowSteps.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = WorkflowSteps.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = WorkflowSteps.findAll()
      entities.foreach(e => WorkflowSteps.destroy(e))
      val batchInserted = WorkflowSteps.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
