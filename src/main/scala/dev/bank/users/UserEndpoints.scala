package dev.bank.users

import zio.ZNothing
import zio.http.RoutePattern
import zio.http.endpoint.{Endpoint, EndpointMiddleware}
import zio.http.endpoint.openapi.OpenAPIGen

object UserEndpoints {
  val health: Endpoint[Unit, Unit, ZNothing, String, EndpointMiddleware.None] = Endpoint(RoutePattern.GET / "health").out[String]
  val userOpenAPI = OpenAPIGen.fromEndpoints(title = "Users Open API", version = "1.0", health)
}
