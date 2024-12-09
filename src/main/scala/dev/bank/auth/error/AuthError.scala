package dev.bank.auth.error

import zio.schema.{DeriveSchema, Schema}

sealed trait AuthError

object AuthError {
  final case class PersonNotFound() extends AuthError

  object PersonNotFound {
    implicit val personNotFoundSchema: Schema[PersonNotFound] = DeriveSchema.gen[PersonNotFound]
  }

  final case class PasswordMismatch() extends AuthError

  object PasswordMismatch {
    implicit val passwordMismatchSchema: Schema[PasswordMismatch] = DeriveSchema.gen[PasswordMismatch]
  }

  final case class DbError() extends AuthError

  object DbError {
    implicit val dbErrorSchema: Schema[DbError] = DeriveSchema.gen[DbError]
  }

  implicit val authErrorSchema: Schema[AuthError] = DeriveSchema.gen[AuthError]
}


