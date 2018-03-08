package io.wonder.soft.example.domain

trait Repository {

  def find(id: Int): Option[Entity]

  def create(entity: Entity): Either[Throwable, Entity]

  def destroy(id: Int): Option[Entity]

  def update(entity: Entity): Either[Throwable, Entity]

}
