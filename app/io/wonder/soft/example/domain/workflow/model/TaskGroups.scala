package io.wonder.soft.example.domain.workflow.model

import org.joda.time.DateTime
import scalikejdbc._

case class TaskGroups(
  id: Int,
  taskId: Int,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = TaskGroups.autoSession): TaskGroups = TaskGroups.save(this)(session)

  def destroy()(implicit session: DBSession = TaskGroups.autoSession): Int = TaskGroups.destroy(this)(session)

}


object TaskGroups extends SQLSyntaxSupport[TaskGroups] {

  override val tableName = "task_groups"

  override val columns = Seq("id", "task_id", "created_at", "updated_at")

  def apply(tg: SyntaxProvider[TaskGroups])(rs: WrappedResultSet): TaskGroups = apply(tg.resultName)(rs)
  def apply(tg: ResultName[TaskGroups])(rs: WrappedResultSet): TaskGroups = new TaskGroups(
    id = rs.get(tg.id),
    taskId = rs.get(tg.taskId),
    createdAt = rs.get(tg.createdAt),
    updatedAt = rs.get(tg.updatedAt)
  )

  val tg = TaskGroups.syntax("tg")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TaskGroups] = {
    withSQL {
      select.from(TaskGroups as tg).where.eq(tg.id, id)
    }.map(TaskGroups(tg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TaskGroups] = {
    withSQL(select.from(TaskGroups as tg)).map(TaskGroups(tg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TaskGroups as tg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TaskGroups] = {
    withSQL {
      select.from(TaskGroups as tg).where.append(where)
    }.map(TaskGroups(tg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TaskGroups] = {
    withSQL {
      select.from(TaskGroups as tg).where.append(where)
    }.map(TaskGroups(tg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TaskGroups as tg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    taskId: Int,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): TaskGroups = {
    val generatedKey = withSQL {
      insert.into(TaskGroups).namedValues(
        column.taskId -> taskId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    TaskGroups(
      id = generatedKey.toInt,
      taskId = taskId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[TaskGroups])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'taskId -> entity.taskId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into task_groups(
      task_id,
      created_at,
      updated_at
    ) values (
      {taskId},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TaskGroups)(implicit session: DBSession = autoSession): TaskGroups = {
    withSQL {
      update(TaskGroups).set(
        column.id -> entity.id,
        column.taskId -> entity.taskId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TaskGroups)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TaskGroups).where.eq(column.id, entity.id) }.update.apply()
  }

}
