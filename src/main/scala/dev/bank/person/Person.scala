package dev.bank.person

import zio.schema.DeriveSchema
import zio.schema.Schema

final case class Person (id: Int, username: String, password: String, moneyAmount: Double)

object Person {
  implicit val personSchema: Schema[Person] = DeriveSchema.gen[Person]
}
