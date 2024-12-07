package dev.bank.person

import dev.bank.MainApp.{getCountry, getUsers}
import dev.bank.person.PersonEndpoints
import dev.bank.person.PersonEndpoints
import dev.bank.person.PersonEndpoints.health
import zio._
import zio.http._
import zio.ZIO
import zio.http.{Handler, Method, Response, Routes, handler}

object PersonRoutes {
  def apply(): Routes[PersonService, Nothing] =
    Routes(
      health.implement[PersonService](
        Handler.fromFunctionZIO {
          _ => for {
            personService <- ZIO.service[PersonService]
            res <- personService.health
          } yield res
        }
      ),
      getUsers.implement[PersonService](
        Handler.fromFunctionZIO {
          _ => for {
            personService <- ZIO.service[PersonService]
            res <- personService.getUsers
          } yield res
        }
      ),
      getCountry.implement[PersonService](
        Handler.fromFunctionZIO {
          _ => for {
            personService <- ZIO.service[PersonService]
            res <- personService.getCountry
          } yield res
        }
      )
    )
}
