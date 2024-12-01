import sbt.*

object Dependencies {
  val zio = "dev.zio"       %% "zio"              % "2.1.1"
  val zioJson = "dev.zio"       %% "zio-json"         % "0.6.2"
  val zioHttp = "dev.zio"       %% "zio-http"         % "3.0.0-RC8"
//  val quill = "io.getquill"   %% "quill-zio"        % "4.7.0"
//  val "io.getquill"   %% "quill-jdbc-zio"   % "4.7.0",
//  "com.h2database" % "h2"               % "2.2.224",
  val zioTest = "dev.zio"       %% "zio-test"         % "2.1.0"
  val zioHttpTest = "dev.zio"       %% "zio-http-testkit" % "3.0.0-RC8"
  val zioSbtTest = "dev.zio"       %% "zio-test-sbt"     % "2.1.1"
}
