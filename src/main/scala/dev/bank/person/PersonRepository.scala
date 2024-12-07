package dev.bank.person

import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.{IO, ZLayer}

import java.sql.SQLException

final case class PersonRepository(quill: Quill.Postgres[SnakeCase]) {
  import quill._


  def getPeople: IO[SQLException, List[Person]] = run(query[Person])
  def getCountry: IO[SQLException, List[Country]] = run(query[Country])
}

object PersonRepository {
  val layer: ZLayer[Quill.Postgres[SnakeCase], Nothing, PersonRepository] = ZLayer.derive[PersonRepository]
}
