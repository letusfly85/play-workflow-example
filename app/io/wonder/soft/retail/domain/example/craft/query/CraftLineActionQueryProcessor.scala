package io.wonder.soft.retail.domain.example.craft.query

import io.wonder.soft.retail.domain.example.craft.entity.CraftLineActionEntity
import io.wonder.soft.retail.domain.example.craft.model.CraftLineActions
import scalikejdbc._

class CraftLineActionQueryProcessor {
  import CraftLineActionEntity._

  val cla = CraftLineActions.syntax("cla")

  def searchByServiceId: List[CraftLineActionEntity] = {
    CraftLineActions.findAllBy(
      sqls.eq(cla.serviceId, CraftLineActionEntity.craftLineServiceId)
    ).map(cla => cla)
  }

}
