package dev.bank.person

import dev.bank.MainApp.{getPerson, getUsers}
import dev.bank.auth.AuthService.{SECRET_TOKEN, jwtDecode}
import dev.bank.auth.error.AuthError.{InvalidToken, MissingClaim, MissingHeader}
import pdi.jwt.JwtClaim
import zio._
import zio.http._
import zio.ZIO
import zio.http.Header.Authorization
import zio.http.Header.Authorization.render
import zio.http.{Handler, Method, Response, Routes, handler}

object PersonRoutes {
  def apply(): Routes[PersonService, Nothing] =
    Routes(
      getUsers.implement[PersonService](
        Handler.fromFunctionZIO {
          _ => for {
            personService <- ZIO.service[PersonService]
            res <- personService.getUsers
          } yield res
        }
      ),
      getPerson.implement[PersonService](Handler.fromFunctionZIO {
        case Authorization.Bearer(token) => for {
          _ <- ZIO.debug(s"Token: ${token.value.asString}")
          claim <- ZIO.fromTry(jwtDecode(token.value.asString, SECRET_TOKEN)).orElseFail(InvalidToken())
          _ <- ZIO.debug(s"JWT claim: $claim")
          username <- ZIO.fromOption(claim.subject).orElseFail(MissingClaim())
          _ <- ZIO.debug(s"Extracted username: $username")
          personService <- ZIO.service[PersonService]
          person <- personService.getPersonByUsername(username)
        } yield person.get
        case _ => ZIO.fail(MissingHeader())
      })
  )
}
