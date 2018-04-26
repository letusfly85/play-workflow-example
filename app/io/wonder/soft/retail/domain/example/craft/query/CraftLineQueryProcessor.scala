package io.wonder.soft.retail.domain.example.craft.query

import io.wonder.soft.retail.domain.example.craft.entity.CraftLineEntity
import io.wonder.soft.retail.domain.example.craft.model.CraftLines
import scalikejdbc._

class CraftLineQueryProcessor {
  import CraftLineEntity._

  val cl = CraftLines.syntax("cl")

  def listCraftLine: List[CraftLineEntity] = CraftLines.findAll().map(o => o)

  def findByTransactionId(transactionId: String): Option[CraftLineEntity] =
    CraftLines.findBy(sqls.eq(cl.transactionId, transactionId)).map(o => o)

}
