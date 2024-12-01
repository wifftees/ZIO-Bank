package dev.bank

import dev.bank.users.UserEndpoints.userOpenAPI
import dev.bank.users.{UserRoutes, UserService}
import zio.http.Server
import zio.http.endpoint.openapi.SwaggerUI
import zio.{ZIO, ZIOAppDefault}
import zio.http.codec.PathCodec.path

object MainApp extends ZIOAppDefault {
  override def run: ZIO[Any, Any, Nothing] = Server.serve(
    UserRoutes() ++ SwaggerUI.routes("docs", userOpenAPI)
  ).provide(
    Server.defaultWithPort(8080),
    UserService.layer
  )
}
