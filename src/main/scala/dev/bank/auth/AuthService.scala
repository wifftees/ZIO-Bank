package dev.bank.auth

import dev.bank.auth.AuthService.{SECRET_TOKEN, checkPassword, jwtEncode}
import dev.bank.auth.error.AuthError
import dev.bank.auth.error.AuthError.{DbError, InvalidToken, MissingClaim, MissingHeader, PasswordMismatch, PersonNotFound}
import dev.bank.person.{Person, PersonRepository}
import pdi.jwt.{JwtAlgorithm, JwtClaim, JwtZIOJson}
import zio.json.{DecoderOps, DeriveJsonDecoder, DeriveJsonEncoder, EncoderOps, JsonDecoder, JsonEncoder}
import zio.{IO, UIO, ZIO, ZLayer}

import java.sql.SQLException
import java.time.Instant
import scala.util.Try

final case class AuthService(personRepository: PersonRepository) {

  def authenticate(loginDTO: LoginDTO): IO[AuthError, String] =
    personRepository
      .getPersonByUsername(loginDTO.username)
      .mapError { _: SQLException => DbError() }
      .flatMap {
        case Some(person) =>
          if (checkPassword(person, loginDTO.password))
            ZIO.succeed(jwtEncode(person.id, person.username, SECRET_TOKEN))
          else ZIO.fail(PasswordMismatch())
        case None => ZIO.fail(PersonNotFound())
      }

}

object AuthService {

  val SECRET_TOKEN     = "secret_token"
  private val TWO_DAYS = 172800

  final case class JwtClaimData(id: Int, username: String)

  object JwtClaimData {
    implicit val jwtClaimDataEncoder: JsonEncoder[JwtClaimData] = DeriveJsonEncoder.gen[JwtClaimData]
    implicit val jwtClaimDataDecoder: JsonDecoder[JwtClaimData] = DeriveJsonDecoder.gen[JwtClaimData]
  }

  def checkPassword(person: Person, password: String): Boolean = person.password == password

  def jwtEncode(id: Int, username: String, key: String): String = JwtZIOJson.encode(
    JwtClaim(
      expiration = Some(Instant.now.plusSeconds(TWO_DAYS).getEpochSecond),
      issuedAt = Some(Instant.now.getEpochSecond),
      // TODO wrap into JwtClaimData
      subject = Some(JwtClaimData(id, username).toJson)
    ),
    key,
    JwtAlgorithm.HS256
  )

  def jwtDecode(token: String, key: String): Try[JwtClaim] =
    JwtZIOJson.decode(token, key, Seq(JwtAlgorithm.HS256))

  def processJwt(token: String): IO[AuthError, JwtClaimData] = for {
    _        <- ZIO.debug(s"Token: $token")
    claim    <- ZIO.fromTry(jwtDecode(token, SECRET_TOKEN)).orElseFail(InvalidToken())
    _        <- ZIO.debug(s"JWT claim: $claim")
    jwtClaimDataJson <- ZIO.fromOption(claim.subject).orElseFail(MissingClaim())
    _        <- ZIO.debug(s"Extracted claim data: $jwtClaimDataJson")
    jwtClaimData <- ZIO.fromEither(jwtClaimDataJson.fromJson[JwtClaimData]).orElseFail(MissingClaim())
  } yield jwtClaimData

  val layer: ZLayer[PersonRepository, Nothing, AuthService] = ZLayer.derive[AuthService]
}
