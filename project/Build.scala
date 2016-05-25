import sbt._
import sbt.Keys._
import xerial.sbt.Pack._

object Build extends sbt.Build {

  lazy val root = Project(
    id = "dbshell",
    base = file("."),
    settings = Defaults.defaultSettings
      ++ packAutoSettings // This settings add pack and pack-archive commands to sbt
      ++ Seq(
      packMain := Map("dbshell" -> "org.freefeeling.DbShell"), // DbShell doesn't work, set packMain only to make sbt-pack generate a executable bash script
      packGenerateWindowsBatFile := false,
      packBashTemplate := "resources/launch-scala.mustache",
      packResourceDir := Map(baseDirectory.value / "resources/preload.scala" -> "bin/preload.scala")
    )
  )
}
