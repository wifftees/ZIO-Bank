package dev.bank.transaction.error

import zio.schema.{DeriveSchema, Schema}

sealed trait TransactionError {}

object TransactionError {
  final case class NotEnoughMoney() extends TransactionError

  object NotEnoughMoney {
    implicit val notEnoughMoneySchema: Schema[NotEnoughMoney] = DeriveSchema.gen[NotEnoughMoney]
  }

  final case class SenderNotFound() extends TransactionError

  object SenderNotFound {
    implicit val senderNotFoundSchema: Schema[SenderNotFound] = DeriveSchema.gen[SenderNotFound]
  }

  final case class ReceiverNotFound() extends TransactionError

  object ReceiverNotFound {
    implicit val receiverNotFoundSchema: Schema[ReceiverNotFound] = DeriveSchema.gen[ReceiverNotFound]
  }

  final case class DbError() extends TransactionError
  object DbError {
    implicit val dbErrorSchema: Schema[DbError] = DeriveSchema.gen[DbError]
  }

  implicit val transactionErrorSchema: Schema[TransactionError] = DeriveSchema.gen[TransactionError]
}
