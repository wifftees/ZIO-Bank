package dev.bank.auth

import dev.bank.MainApp.login
import zio.ZIO
import zio.http.{Handler, Routes, handler}

object AuthRoutes {
  def apply(): Routes[AuthService, Nothing] = Routes(
    login.implement[AuthService](
      Handler.fromFunctionZIO {
        loginDTO => for {
          personService <- ZIO.service[AuthService]
          response <- personService.authenticate(loginDTO)
        } yield response
      }
    )
  )
}
