package dev.bank.transaction

import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.{IO, ZLayer}

import java.sql.SQLException

final case class TransactionRepository(quill: Quill.Postgres[SnakeCase]) {
  import quill._

  def getTransactions(userId: Int): IO[SQLException, List[Transaction]] = run(query[Transaction].filter(
    transaction => (transaction.senderId == lift(userId)) || (transaction.receiverId == lift(userId))))

}

object TransactionRepository {
  val layer: ZLayer[Quill.Postgres[SnakeCase], Nothing, TransactionRepository] =
    ZLayer.derive[TransactionRepository]
}
