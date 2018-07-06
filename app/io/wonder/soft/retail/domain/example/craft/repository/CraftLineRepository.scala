package io.wonder.soft.retail.domain.example.craft.repository

import io.wonder.soft.retail.domain.example.craft.entity.CraftLineEntity

trait CraftLineRepository {

  def find(id: Int): Option[CraftLineEntity]

  def create(entity: CraftLineEntity): Either[Exception, CraftLineEntity]

  def update(entity: CraftLineEntity): Either[RuntimeException, CraftLineEntity]

  def destroy(id: Int): Option[CraftLineEntity]
}

