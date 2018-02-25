import sbt.Keys._
import sbt._

object Dependencies {
  private val junit : ModuleID = "junit" % "junit" % "4.12" % Test withSources() withJavadoc()
  private val junitInterface : ModuleID = "com.novocode" % "junit-interface" % "0.11" % Test withSources() withJavadoc()
  private val hamcrestCore : ModuleID = "org.hamcrest" % "hamcrest-core" % "1.3" % Test withSources() withJavadoc()
  private val scalactic : ModuleID =  "org.scalactic" %% "scalactic" % "3.0.1"
  private val scalatest : ModuleID =  "org.scalatest" %% "scalatest" % "3.0.1"

  val testDeps : Seq[ModuleID] = Seq(junit, junitInterface, hamcrestCore)

  lazy val commonSettings: Seq[Def.Setting[String]] = Seq(
    organization := "ekis",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.12.4",
    name := baseDirectory.value.getName
    //name := "Algorithms Implementation"
  )
}
