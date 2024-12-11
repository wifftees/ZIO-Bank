package dev.bank.auth

import dev.bank.auth.AuthService.{JwtClaimData, SECRET_TOKEN, TWO_DAYS, checkPassword, jwtEncode}
import dev.bank.auth.error.AuthError
import dev.bank.auth.error.AuthError.{DbError, InvalidToken, MissingClaim, MissingHeader, PasswordMismatch, PersonNotFound}
import dev.bank.person.{Person, PersonRepository}
import pdi.jwt.{JwtAlgorithm, JwtClaim, JwtZIOJson}
import zio.http.{Handler, HandlerAspect, Header, Headers, Middleware, Request, Response, Routes}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, EncoderOps, JsonDecoder, JsonEncoder}
import zio.{IO, UIO, ZIO, ZLayer}

import java.sql.SQLException
import java.time.Instant
import scala.util.Try

final case class AuthService(personRepository: PersonRepository) {
  def authenticate(loginDTO: LoginDTO): IO[AuthError, String] =
    personRepository.getPersonByUsername(loginDTO.username).mapError {
      _: SQLException => DbError()
    }.flatMap {
      case Some(person) => if (checkPassword(person, loginDTO.password))
        ZIO.succeed(jwtEncode(person.username, SECRET_TOKEN)) else ZIO.fail(PasswordMismatch())
      case None => ZIO.fail(PersonNotFound())
    }
}

object AuthService {
  final case class JwtClaimData(username: String)

  object JwtClaimData {
    implicit val encoder: JsonEncoder[JwtClaimData] = DeriveJsonEncoder.gen[JwtClaimData]
    implicit val decoder: JsonDecoder[JwtClaimData] = DeriveJsonDecoder.gen[JwtClaimData]
  }

  val SECRET_TOKEN = "secret_token"
  private val TWO_DAYS = 172800

  def checkPassword(person: Person, password: String): Boolean = person.password == password

  def jwtEncode(username: String, key: String): String = JwtZIOJson.encode(
    JwtClaim(
      expiration = Some(Instant.now.plusSeconds(TWO_DAYS).getEpochSecond),
      issuedAt = Some(Instant.now.getEpochSecond),
      // TODO wrap into JwtClaimData
      subject = Some(username)
    ), key, JwtAlgorithm.HS256
  )

  def jwtDecode(token: String, key: String): Try[JwtClaim] = JwtZIOJson.decode(token, key, Seq(JwtAlgorithm.HS256))

  val layer: ZLayer[PersonRepository, Nothing, AuthService] = ZLayer.derive[AuthService]
}
