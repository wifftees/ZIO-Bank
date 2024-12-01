package dev.bank.users

import dev.bank.users.UserRoutesHandler.OK
import zio.{UIO, ZIO, ZLayer}

case class UserRoutesHandler () {
  def health: UIO[String] = ZIO.succeed(OK)
}

object UserRoutesHandler {
  private val OK = "ok"
  val layer: ZLayer[Any, Nothing, UserRoutesHandler] = ZLayer.derive[UserRoutesHandler]
}
