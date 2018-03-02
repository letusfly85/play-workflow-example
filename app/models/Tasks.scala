package models

import scalikejdbc._
import org.joda.time.{DateTime}

case class Tasks(
  id: Int,
  name: String,
  description: Option[String] = None,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = Tasks.autoSession): Tasks = Tasks.save(this)(session)

  def destroy()(implicit session: DBSession = Tasks.autoSession): Int = Tasks.destroy(this)(session)

}


object Tasks extends SQLSyntaxSupport[Tasks] {

  override val schemaName = Some("simple_workflow")

  override val tableName = "tasks"

  override val columns = Seq("id", "name", "description", "created_at", "updated_at")

  def apply(t: SyntaxProvider[Tasks])(rs: WrappedResultSet): Tasks = apply(t.resultName)(rs)
  def apply(t: ResultName[Tasks])(rs: WrappedResultSet): Tasks = new Tasks(
    id = rs.get(t.id),
    name = rs.get(t.name),
    description = rs.get(t.description),
    createdAt = rs.get(t.createdAt),
    updatedAt = rs.get(t.updatedAt)
  )

  val t = Tasks.syntax("t")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Tasks] = {
    withSQL {
      select.from(Tasks as t).where.eq(t.id, id)
    }.map(Tasks(t.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Tasks] = {
    withSQL(select.from(Tasks as t)).map(Tasks(t.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Tasks as t)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Tasks] = {
    withSQL {
      select.from(Tasks as t).where.append(where)
    }.map(Tasks(t.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Tasks] = {
    withSQL {
      select.from(Tasks as t).where.append(where)
    }.map(Tasks(t.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Tasks as t).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    description: Option[String] = None,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): Tasks = {
    val generatedKey = withSQL {
      insert.into(Tasks).namedValues(
        column.name -> name,
        column.description -> description,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Tasks(
      id = generatedKey.toInt,
      name = name,
      description = description,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[Tasks])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'description -> entity.description,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into tasks(
      name,
      description,
      created_at,
      updated_at
    ) values (
      {name},
      {description},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Tasks)(implicit session: DBSession = autoSession): Tasks = {
    withSQL {
      update(Tasks).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.description -> entity.description,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Tasks)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Tasks).where.eq(column.id, entity.id) }.update.apply()
  }

}
