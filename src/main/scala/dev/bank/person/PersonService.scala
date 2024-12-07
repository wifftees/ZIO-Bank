package dev.bank.person

import dev.bank.person.PersonService.OK
import zio.{IO, UIO, ZIO, ZLayer}

import java.sql.SQLException

final case class PersonService (repository: PersonRepository) {
  def health: UIO[String] = ZIO.succeed(OK)
  def getUsers: UIO[List[Person]] = repository.getPeople.catchAll { error =>
    println(s"Error occurred: $error") // Log the error using println
    ZIO.succeed(List.empty[Person])
  }
  def getCountry: UIO[List[Country]] = repository.getCountry.catchAll { error =>
    println(s"Error occurred: $error") // Log the error using println
    ZIO.succeed(List.empty[Country])
  }
}

object PersonService {
  private val OK = "ok"
  val layer: ZLayer[PersonRepository, Nothing, PersonService] = ZLayer.derive[PersonService]
}
