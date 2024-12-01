package dev.bank.users

import zio.ZIO
import zio.http.{Method, Response, Routes, handler}

object UserRoutes {
  def apply(): Routes[UserRoutesHandler, Response] =
    Routes(
      Method.GET / "health" -> handler {
        ZIO.serviceWithZIO[UserRoutesHandler](_.health.map(Response.text))
      }
    )
}
