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
//      zioConfig,
//      zioConfigTypesafe,
//      zioConfigMagnolia,
      slf4jApi,
      slf4Simple,
      quill,
      quillJdbc,
      postgresql,
      zioTest % Test,
      zioHttpTest % Test,
      zioSbtTest % Test
    )
  )
