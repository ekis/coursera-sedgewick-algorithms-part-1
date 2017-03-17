import Dependencies._

lazy val root : Project = (project in file("."))
  .aggregate(common, commonTest, week1, week2, week3, week4)
  .settings(commonSettings : _*)

lazy val common : Project = (project in file("common"))
  .settings(commonSettings : _*)

lazy val commonTest : Project = (project in file("commonTest"))
  .dependsOn(common % "compile -> test")
  .settings(commonSettings : _*)
  .settings(libraryDependencies ++= testDeps)

lazy val week1 : Project = (project in file("week1"))
  .dependsOn(common, commonTest % "test -> test")
  .settings(commonSettings : _*)
  .settings(testOptions ++= commonTestOptions)

lazy val week2 : Project = (project in file("week2"))
  .dependsOn(common, commonTest % "test -> test")
  .settings(commonSettings : _*)
  .settings(testOptions ++= commonTestOptions)

lazy val week3 : Project = (project in file("week3"))
  .dependsOn(common, commonTest % "test -> test")
  .settings(commonSettings : _*)
  .settings(testOptions ++= commonTestOptions)

lazy val week4 : Project = (project in file("week4"))
  .dependsOn(week2, common, commonTest % "test -> test")
  .settings(commonSettings : _*)
  .settings(testOptions ++= commonTestOptions)

lazy val week5 : Project = (project in file("week5"))
  .dependsOn(common, commonTest % "test -> test")
  .settings(commonSettings : _*)
  .settings(testOptions ++= commonTestOptions)

lazy val week6 : Project = (project in file("week6"))
  .dependsOn(common, commonTest % "test -> test")
  .settings(commonSettings : _*)
  .settings(testOptions ++= commonTestOptions)

logBuffered in Test := false

def commonTestOptions: Seq[TestOption] = Seq(
  Tests.Filter(t => t.endsWith("Test")),
  Tests.Argument(TestFrameworks.JUnit, "-v", "-q") // "-q" -> try to suppress stdout for successful tests; "-v" -> log start/end test on log level "INFO"
)
