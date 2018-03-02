package io.wonder.soft.example.domain

import play.api.libs.functional.syntax._
import play.api.libs.json._

trait Entity

/*
object Entity {
  implicit def leaveWrites: Writes[Entity] = (__ \ "entity").write[Entity].contramap(entity => entity)
}
*/
