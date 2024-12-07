package dev.bank.person

import zio.schema.DeriveSchema
import zio.schema.Schema

final case class Person (id: Int, name: String, moneyAmount: Double)

final case class Country(id: Int, name: String)

object Person {
  implicit val personSchema: Schema[Person] = DeriveSchema.gen[Person]
}

object Country {
  implicit val countrySchema: Schema[Country] = DeriveSchema.gen[Country]
}
