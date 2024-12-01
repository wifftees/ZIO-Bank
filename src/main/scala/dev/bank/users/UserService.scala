package dev.bank.users

import dev.bank.users.UserService.OK
import zio.{UIO, ZIO, ZLayer}

case class UserService () {
  def health: UIO[String] = ZIO.succeed(OK)
}

object UserService {
  private val OK = "ok"
  val layer: ZLayer[Any, Nothing, UserService] = ZLayer.derive[UserService]
}
