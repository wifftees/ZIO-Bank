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
      zioConfig,
      zioConfigTypesafe,
      zioConfigMagnolia,
      zioTest % Test,
      zioHttpTest % Test,
      zioSbtTest % Test
    )
  )

enablePlugins(ScalafmtPlugin)
enablePlugins(AssemblyPlugin)
Compile / mainClass := Some("dev.bank.MainApp")
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "io.netty.versions.properties") =>
    MergeStrategy.first // Keep the first file found
  case PathList("META-INF", _@_*) =>
    MergeStrategy.discard // Discard other META-INF files
  case _ => MergeStrategy.first // Use default strategy for other cases
}