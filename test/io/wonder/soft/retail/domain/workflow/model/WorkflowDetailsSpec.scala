package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._


class WorkflowDetailsSpec extends Specification {

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
      val entity = WorkflowDetails.findAll().head
      // TODO modify something
      val modified = entity
      val updated = WorkflowDetails.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = WorkflowDetails.findAll().head
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
