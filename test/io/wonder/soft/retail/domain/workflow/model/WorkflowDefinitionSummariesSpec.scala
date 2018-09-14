package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._


class WorkflowDefinitionSummariesSpec extends Specification {

  "WorkflowDefinitionSummaries" should {

    val wds = WorkflowDefinitionSummaries.syntax("wds")

    "find by primary keys" in new AutoRollback {
      val maybeFound = WorkflowDefinitionSummaries.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = WorkflowDefinitionSummaries.findBy(sqls.eq(wds.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = WorkflowDefinitionSummaries.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = WorkflowDefinitionSummaries.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = WorkflowDefinitionSummaries.findAllBy(sqls.eq(wds.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = WorkflowDefinitionSummaries.countBy(sqls.eq(wds.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = WorkflowDefinitionSummaries.create(workflowId = 123, name = "MyString", serviceId = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = WorkflowDefinitionSummaries.findAll().head
      // TODO modify something
      val modified = entity
      val updated = WorkflowDefinitionSummaries.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = WorkflowDefinitionSummaries.findAll().head
      val deleted = WorkflowDefinitionSummaries.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = WorkflowDefinitionSummaries.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = WorkflowDefinitionSummaries.findAll()
      entities.foreach(e => WorkflowDefinitionSummaries.destroy(e))
      val batchInserted = WorkflowDefinitionSummaries.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
