package dev.bank.users

import dev.bank.users.UserEndpoints
import zio._
import zio.http._
import zio.ZIO
import zio.http.{Handler, Method, Response, Routes, handler}

object UserRoutes {
  def apply(): Routes[UserService, Response] =
    Routes(
//      Method.GET / "health" -> handler {
//        ZIO.serviceWithZIO[UserRoutesHandler](_.health.map(Response.text))
//      }
      UserEndpoints.health.implement[UserService](
        Handler.fromFunctionZIO {
          _ => for {
            userService <- ZIO.service[UserService]
            res <- userService.health
          } yield res
        }
      )
//      UserEndpoints.health.implement {
//        Handler.fromFunction(_ => "ok")
//      }
    )
}
