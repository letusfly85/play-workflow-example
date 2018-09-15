package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.DateTime
import org.specs2.specification.BeforeAfterAll
import scalikejdbc.config.DBs
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._


class WorkflowDetailsSpec extends Specification with BeforeAfterAll {
  DBs.setupAll()

  def beforeAll = {
    val testWorkflowId = 99
    val testWorkflowName = "test"
    DB localTx { implicit session =>
      SQL("delete from workflow_details where id = ?").bind(123).update.apply
      SQL("insert into workflow_details (id, workflow_id, name, status_id, step_id, step_label, is_first_step, is_last_step) values (?, ?, ?, ?, ?, ?, ?, ?)")
        .bind(123, testWorkflowId, testWorkflowName, 99, 1, "step1", true, false).update.apply
    }
  }

  def afterAll = {
    DB localTx { implicit session =>
      SQL("delete from workflow_details where id = ?").bind(123).update.apply
    }
  }

  "WorkflowDetails" should {

    val wd = WorkflowDetails.syntax("wd")

    "find by primary keys" in new AutoRollback {
      val maybeFound = WorkflowDetails.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = WorkflowDetails.findBy(sqls.eq(wd.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = WorkflowDetails.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = WorkflowDetails.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = WorkflowDetails.findAllBy(sqls.eq(wd.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = WorkflowDetails.countBy(sqls.eq(wd.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = WorkflowDetails.create(workflowId = 123, name = "MyString", statusId = 123, stepId = 123, stepLabel = "MyString", isFirstStep = false, isLastStep = false)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = WorkflowDetails.find(123).get
      val modified = entity.copy(name = "hoge")
      val updated = WorkflowDetails.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = WorkflowDetails.find(123).get
      val deleted = WorkflowDetails.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = WorkflowDetails.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = WorkflowDetails.findAll()
      entities.foreach(e => WorkflowDetails.destroy(e))
      val batchInserted = WorkflowDetails.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
