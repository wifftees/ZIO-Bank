package dev.bank.person

import zio.{IO, UIO, ZIO, ZLayer}

import java.sql.SQLException

final case class PersonService(repository: PersonRepository) {

  def getUsers: UIO[List[Person]] = repository.getPeople.orElseSucceed(List.empty[Person])

  def getPersonByUsername(username: String): UIO[Option[Person]] =
    repository.getPersonByUsername(username).orElseSucceed(None)

}

object PersonService {
  val layer: ZLayer[PersonRepository, Nothing, PersonService] = ZLayer.derive[PersonService]
}
