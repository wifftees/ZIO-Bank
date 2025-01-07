package dev.bank.transaction

import zio.schema.{DeriveSchema, Schema}

final case class TransactionDTO(receiverId: Int, amount: Double)

object TransactionDTO {
  implicit val transactionDTO: Schema[TransactionDTO] = DeriveSchema.gen[TransactionDTO]
}
