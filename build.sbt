organization := "freefeeling"
name := "dbshell"

version := "0.0.1-SNAPSHOT"
scalaVersion := "2.11.8"

lazy val dependencies = Seq(
  "com.typesafe" % "config" % "1.2.1"
  , "mysql" % "mysql-connector-java" % "5.1.18"
  , "org.scalikejdbc" %% "scalikejdbc" % "2.3.5"
  , "io.github.netvl.picopickle" %% "picopickle-core" % "0.3.0"
)

lazy val hbaseShell = Seq(

)

libraryDependencies ++= dependencies
