import Dependencies.*
import sbt.librarymanagement.Configurations.Test

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "bank",
    libraryDependencies ++= List(
      zio,
      zioJson,
      zioHttp,
      jwt,
      zioLogging,
      quill,
      quillJdbc,
      postgresql,
      zioTest % Test,
      zioHttpTest % Test,
      zioSbtTest % Test
    )
  )
