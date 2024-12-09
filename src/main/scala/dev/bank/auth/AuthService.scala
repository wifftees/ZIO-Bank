package dev.bank.auth

import dev.bank.auth.error.AuthError
import dev.bank.auth.error.AuthError.{DbError, PasswordMismatch, PersonNotFound}
import dev.bank.person.{Person, PersonRepository}
import zio.{IO, UIO, ZIO, ZLayer}

import java.sql.SQLException

final case class AuthService(personRepository: PersonRepository) {
  def authenticate(loginDTO: LoginDTO): IO[AuthError, String] =
    personRepository.getPersonByUsername(loginDTO.username).mapError {
      _: SQLException => DbError()
    }.flatMap {
      case Some(person) => if (checkPassword(person, loginDTO.password)) ZIO.succeed("SUCCESS") else ZIO.fail(PasswordMismatch())
      case None => ZIO.fail(PersonNotFound())
    }
  private def checkPassword(person: Person, password: String): Boolean = person.password == password
}

object AuthService {
  val layer: ZLayer[PersonRepository, Nothing, AuthService] = ZLayer.derive[AuthService]
}
