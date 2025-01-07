package dev.bank.transaction

import zio.{UIO, ZLayer}

final case class TransactionService(repository: TransactionRepository) {

  def getTransactions(userId: Int): UIO[History] = for {
    transactionOfUser <- repository.getTransactions(userId).orElseSucceed(List.empty[Transaction])
    incomingTransactions = transactionOfUser.filter(_.receiverId == userId)
    outgoingTransactions = transactionOfUser.filter(_.senderId == userId)
  } yield History(incomingTransactions, outgoingTransactions)

}

object TransactionService {

  val layer: ZLayer[TransactionRepository, Nothing, TransactionService] =
    ZLayer.derive[TransactionService]

}
