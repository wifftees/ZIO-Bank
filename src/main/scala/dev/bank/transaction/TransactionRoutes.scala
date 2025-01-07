package dev.bank.transaction

import dev.bank.MainApp.getTransactions
import dev.bank.auth.AuthService
import zio.ZIO
import zio.http.Header.Authorization.Bearer
import zio.http.{Handler, Routes}

object TransactionRoutes {

  def apply(): Routes[TransactionService, Nothing] =
    Routes(
      getTransactions.implement[TransactionService](
        Handler.fromFunctionZIO { case Bearer(token) =>
          for {
            jwtClaimData       <- AuthService.processJwt(token.value.asString)
            transactionService <- ZIO.service[TransactionService]
            history            <- transactionService.getTransactions(jwtClaimData.id)
          } yield history
        }
      )
    )

}
