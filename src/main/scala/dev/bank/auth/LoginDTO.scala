package dev.bank.auth

import zio.schema.{DeriveSchema, Schema}

case class LoginDTO (username: String, password: String)

object LoginDTO {
  implicit val loginDTOSchema: Schema[LoginDTO] = DeriveSchema.gen[LoginDTO]
}
