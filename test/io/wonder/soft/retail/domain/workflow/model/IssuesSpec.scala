package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._


class IssuesSpec extends Specification {

  /*
  "Issues" should {

    val i = Issues.syntax("i")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Issues.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Issues.findBy(sqls.eq(i.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Issues.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Issues.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Issues.findAllBy(sqls.eq(i.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Issues.countBy(sqls.eq(i.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Issues.create(issueId = "MyString", statusId = "MyString")
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Issues.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Issues.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Issues.findAll().head
      val deleted = Issues.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Issues.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Issues.findAll()
      entities.foreach(e => Issues.destroy(e))
      val batchInserted = Issues.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }
  */

}
