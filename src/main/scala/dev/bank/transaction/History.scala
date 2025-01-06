package dev.bank.transaction

import zio.schema.{DeriveSchema, Schema}

final case class History (incoming: List[Transaction], outgoing: List[Transaction])

object History {
  implicit val historySchema: Schema[History] = DeriveSchema.gen[History]
}


