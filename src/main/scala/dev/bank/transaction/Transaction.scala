package dev.bank.transaction

import zio.schema.{DeriveSchema, Schema}

final case class Transaction(id: Int, senderId: Int, receiverId: Int, amount: Double)

object Transaction {
  implicit val transactionSchema: Schema[Transaction] = DeriveSchema.gen[Transaction]
}

