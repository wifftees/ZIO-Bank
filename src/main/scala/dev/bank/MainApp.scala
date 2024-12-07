package dev.bank

import dev.bank.person.{Country, Person, PersonRepository, PersonRoutes, PersonService}
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.http.{Handler, Routes, Server}
import zio.http.endpoint.openapi.SwaggerUI
import zio.{ZIO, ZIOAppDefault}
import zio.http.codec.PathCodec.path

import javax.sql.DataSource

//object MainApp extends ZIOAppDefault {
//  override def run: ZIO[Any, Any, Nothing] = Server.serve(
//   Routes(UserEndpoints.getUsers.implement(Handler.fromFunction(_ => List(User(1, "Kevin", 2.0), User(2, "Jack", 3.0)))))
//      ++ SwaggerUI.routes("docs", userOpenAPI)
//  ).provide(
//    Server.defaultWithPort(8080), //    UserService.layer //  ) //}
import zio._


import zio.http._
import zio.http.codec._
import zio.http.endpoint._
import zio.http.endpoint.openapi._

object MainApp extends ZIOAppDefault {
//  val endpoint =
//    Endpoint((RoutePattern.GET / "books") ?? Doc.p("Route for querying books"))
//      .out[List[User]](Doc.p("List of books matching the query")) ?? Doc.p(
//      "Endpoint to query books based on a search query",
//    )
//
  val getUsers = Endpoint(RoutePattern.GET / "people")
    .out[List[Person]]
   // val endpoint = UserEndpoints.getUsers
  val getCountry = Endpoint(RoutePattern.GET / "country").out[List[Country]]

   // val booksRoute = getUsers.implement(Handler.fromFunction(_ => List(User(1, "Kevin", 2.0), User(2, "Jack", 3.0))))
  val userOpenAPI = OpenAPIGen.fromEndpoints(title = "Users Open API", version = "1.0", getUsers, getCountry)
  val swaggerRoutes = SwaggerUI.routes("docs" / "openapi", userOpenAPI)
  val routes = PersonRoutes() ++ swaggerRoutes

  val quillLayer = Quill.Postgres.fromNamingStrategy(SnakeCase)
  val dataSourceLayer: ZLayer[Any, Throwable, DataSource] = Quill.DataSource.fromPrefix("dbConfig")

  def run = Server.serve(routes).provide(Server.default, quillLayer, dataSourceLayer,
    PersonService.layer, PersonRepository.layer)
}

