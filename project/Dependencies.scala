import sbt.*

object Dependencies {
  val zio = "dev.zio"       %% "zio"              % "2.1.1"
  val zioJson = "dev.zio"       %% "zio-json"         % "0.6.2"
  val zioHttp = "dev.zio"       %% "zio-http"         % "3.0.0-RC8"
//  val zioConfig = "dev.zio" %% "zio-config" % "4.0.2"
//  val zioConfigTypesafe = "dev.zio" %% "zio-config-typesafe" % "4.0.2"
//  val zioConfigMagnolia = "dev.zio" %% "zio-config-magnolia" % "4.0.2"

  val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.32"
  val slf4Simple = "org.slf4j" % "slf4j-simple" % "1.7.32"
  val quill = "io.getquill"   %% "quill-zio"        % "4.7.0"
  val quillJdbc = "io.getquill"   %% "quill-jdbc-zio"   % "4.7.0"
  val postgresql = "org.postgresql" % "postgresql" % "42.7.4"
  val zioTest = "dev.zio"       %% "zio-test"         % "2.1.0"
  val zioHttpTest = "dev.zio"       %% "zio-http-testkit" % "3.0.0-RC8"
  val zioSbtTest = "dev.zio"       %% "zio-test-sbt"     % "2.1.1"
  ,
}
