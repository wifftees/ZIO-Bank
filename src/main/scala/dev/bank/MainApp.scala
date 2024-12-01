package dev.bank

import dev.bank.users.{UserRoutes, UserRoutesHandler}
import zio.http.Server
import zio.{ZIO, ZIOAppDefault}

object MainApp extends ZIOAppDefault {


  override def run: ZIO[Any, Any, Nothing] = Server.serve(
    UserRoutes()
  ).provide(
    Server.defaultWithPort(8080),
    UserRoutesHandler.layer
  )
}
