package dev.bank

import dev.bank.auth.{AuthRoutes, AuthService, LoginDTO}
import dev.bank.auth.error.AuthError
import dev.bank.auth.error.AuthError.{
  DbError,
  InvalidToken,
  MissingClaim,
  MissingHeader,
  PasswordMismatch,
  PersonNotFound
}
import dev.bank.person.{Person, PersonRepository, PersonRoutes, PersonService}
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.http.codec.HttpCodec
import zio.http.{Handler, Routes, Server}
import zio.http.endpoint.openapi.SwaggerUI
import zio.{ZIO, ZIOAppDefault}
import zio.http.codec.PathCodec.path
import zio.http.endpoint.EndpointMiddleware.Typed

import javax.sql.DataSource
import zio._
import zio.http._
import zio.http.codec._
import zio.http.endpoint._
import zio.http.endpoint.openapi._
import zio.logging.consoleLogger

object MainApp extends ZIOAppDefault {

  val getPerson = Endpoint(RoutePattern.GET / "person")
    .header(HeaderCodec.authorization)
    .out[Person]
    .outErrors[AuthError](
      HttpCodec.error[MissingClaim](Status.Forbidden),
      HttpCodec.error[InvalidToken](Status.Forbidden),
      HttpCodec.error[MissingHeader](Status.Forbidden)
    )

  val getUsers = Endpoint(RoutePattern.GET / "people")
    .out[List[Person]]

  val login = Endpoint(RoutePattern.POST / "login")
    .in[LoginDTO]
    .out[String]
    .outErrors[AuthError](
      HttpCodec.error[DbError](Status.Unauthorized),
      HttpCodec.error[PersonNotFound](Status.Unauthorized),
      HttpCodec.error[PasswordMismatch](Status.Unauthorized)
    )

  val userOpenAPI =
    OpenAPIGen.fromEndpoints(title = "Users Open API", version = "1.0", getUsers, getPerson)

  val authOpenAPI   = OpenAPIGen.fromEndpoints(title = "Auth Open API", version = "1.0", login)
  val swaggerRoutes = SwaggerUI.routes("docs", userOpenAPI, authOpenAPI)
  val routes        = PersonRoutes() ++ AuthRoutes() ++ swaggerRoutes

  val quillLayer = Quill.Postgres.fromNamingStrategy(SnakeCase)
  val dataSourceLayer: ZLayer[Any, Throwable, DataSource] = Quill.DataSource.fromPrefix("dbConfig")

  override val bootstrap = Runtime.removeDefaultLoggers >>> consoleLogger()

  def run = Server
    .serve(routes)
    .provide(
      Server.default,
      quillLayer,
      dataSourceLayer,
      PersonService.layer,
      PersonRepository.layer,
      AuthService.layer
    )

}
