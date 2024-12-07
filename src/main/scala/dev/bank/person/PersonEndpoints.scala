package dev.bank.person

import zio.ZNothing
import zio.http.endpoint.grpc.GRPC.fromSchema
import zio.http.{RoutePattern}
import zio.http.endpoint.{Endpoint, EndpointMiddleware}
import zio.http.endpoint.openapi.{OpenAPI, OpenAPIGen}

object PersonEndpoints {
  val health = Endpoint(RoutePattern.GET / "health")
    .out[String]
  val getPeople = Endpoint(RoutePattern.GET / "people")
    .out[List[Person]]
  val personOpenAPI = OpenAPIGen.fromEndpoints(title = "Users Open API", version = "1.0", getPeople)
}
