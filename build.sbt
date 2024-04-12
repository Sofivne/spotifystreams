ThisBuild / scalaVersion     := "3.3.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "spotify_stream",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.21",
      "dev.zio" %% "zio-test" % "2.0.21" % Test,
      "dev.zio" %% "zio-streams" % "2.0.20",
      "com.github.tototoshi" %% "scala-csv" % "1.3.10",
      "dev.zio" %% "zio-http" % "3.0.0-RC3",
      "io.d11" %% "zhttp" % "1.0.0.0-RC17"

    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
