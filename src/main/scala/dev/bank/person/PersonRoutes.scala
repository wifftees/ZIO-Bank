package dev.bank.person

import dev.bank.MainApp.{getPeople, getPerson}
import dev.bank.auth.AuthService
import dev.bank.auth.error.AuthError.{InvalidToken, MissingClaim, MissingHeader}
import zio.ZIO
import zio.http.Header.Authorization
import zio.http.Header.Authorization.Bearer
import zio.http.{Handler, Method, Response, Routes, handler}

object PersonRoutes {

  def apply(): Routes[PersonService, Nothing] =
    Routes(
      getPeople.implement[PersonService](
        Handler.fromFunctionZIO { _ =>
          for {
            personService <- ZIO.service[PersonService]
            res           <- personService.getUsers
          } yield res
        }
      ),
      getPerson.implement[PersonService](Handler.fromFunctionZIO {
        case Bearer(token) =>
          for {
            jwtClaimData      <- AuthService.processJwt(token.value.asString)
            personService <- ZIO.service[PersonService]
            person        <- personService.getPersonByUsername(jwtClaimData.username)
            // TODO remove unsafe get method
          } yield person.get
        case _ => ZIO.fail(MissingHeader())
      })
    )

}
