package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._


class WorkflowsSpec extends Specification {

  "Workflows" should {

    val w = Workflows.syntax("w")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Workflows.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Workflows.findBy(sqls.eq(w.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Workflows.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Workflows.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Workflows.findAllBy(sqls.eq(w.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Workflows.countBy(sqls.eq(w.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Workflows.create(workflowId = 123, name = "MyString", serviceId = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Workflows.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Workflows.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Workflows.findAll().head
      val deleted = Workflows.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Workflows.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Workflows.findAll()
      entities.foreach(e => Workflows.destroy(e))
      val batchInserted = Workflows.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
