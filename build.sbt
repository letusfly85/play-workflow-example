organization := "io.wonder.soft"

name := "retail.workflow"

val _version = "0.0.1"

version := s"${_version}-SNAPSHOT"

lazy val `retail-workflow` = (project in file(".")).enablePlugins(PlayScala, ScalikejdbcPlugin, FlywayPlugin)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "Atlassian Releases" at "https://maven.atlassian.com/public/"

scalaVersion := "2.12.8"

libraryDependencies ++= {
  val silhouetteVersion = "5.0.6"
  val scalikeJDBCV = "3.3.2"
  val spec2V = "4.3.3"
  val logbackV = "1.2.3"
  val logbackJsonV = "0.1.5"
  val jacksonV = "2.8.9" // 2.9.3 not working with logback-json dependencies
  Seq(
    jdbc,
    ehcache,
    ws,
    specs2 % Test,
    guice,

    //macwire
    "com.softwaremill.macwire" %% "macros" % "2.3.1" % "provided",

    //silhouette dependencies
    "com.mohiva" %% "play-silhouette" % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-password-bcrypt" % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-persistence" % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-crypto-jca" % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-testkit" % silhouetteVersion,

    //injection dependencies
    "com.iheart" %% "ficus" % "1.4.1",
    "net.codingwell" %% "scala-guice" % "4.1.0",

    //ScalikeJDBC dependencies
    "org.scalikejdbc" %% "scalikejdbc"                     % scalikeJDBCV,
    "org.scalikejdbc" %% "scalikejdbc-config"              % scalikeJDBCV,
    "org.scalikejdbc" %% "scalikejdbc-joda-time"           % scalikeJDBCV,
    "org.scalikejdbc" %% "scalikejdbc-play-initializer"    % "2.6.0-scalikejdbc-3.3",
    "org.scalikejdbc" %% "scalikejdbc-test" % scalikeJDBCV % Test,
    "mysql" % "mysql-connector-java" % "5.1.47",

    "com.iheart" %% "ficus" % "1.4.1",
    "com.typesafe.play" %% "play-mailer" % "6.0.1",
    "com.typesafe.play" %% "play-mailer-guice" % "6.0.1",
    "com.enragedginger" %% "akka-quartz-scheduler" % "1.6.1-akka-2.5.x",

    "org.typelevel" %% "cats-core" % "1.0.1",

    // logback dependencies
    "org.slf4j" % "slf4j-api" % "1.7.25",
    "ch.qos.logback" % "logback-core" % logbackV,
    "ch.qos.logback" % "logback-classic" % logbackV,
    "ch.qos.logback.contrib" % "logback-json-core" % logbackJsonV,
    "ch.qos.logback.contrib" % "logback-json-classic" % logbackJsonV,
    "ch.qos.logback.contrib" % "logback-jackson" % logbackJsonV,
    "com.fasterxml.jackson.core" % "jackson-core" % jacksonV,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonV,
    "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonV,

    "org.specs2" %% "specs2-core" % spec2V % Test,
    "org.specs2" %% "specs2-mock" % spec2V % Test,
    "org.specs2" %% "specs2-common" % spec2V % Test,
    "org.specs2" %% "specs2-junit" % spec2V % Test,
    "org.specs2" %% "specs2-matcher" % spec2V % Test,
    "org.scalaz" %% "scalaz-core" % "7.2.24",
    "org.mockito" % "mockito-core" % "2.21.0" % Test,

    //ScalaCheck
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
  )
}

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:implicitConversions",
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  //"-Xlint", // Enable recommended additional warnings.
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  // Play has a lot of issues with unused imports and unsued params
  // https://github.com/playframework/playframework/issues/6690
  // https://github.com/playframework/twirl/issues/105
  "-Xlint:-unused,_",
  // https://github.com/typelevel/cats
  "-Ypartial-unification"
)

//********************************************************
// FlyWay settings
//********************************************************
import com.typesafe.config._

val conf = ConfigFactory.parseFile(new File("conf/database.flyway.conf")).resolve()

flywayDriver := conf.getString("db.default.driver")

flywayUrl := conf.getString("db.default.url")

flywayUser := conf.getString("db.default.username")

flywayPassword := conf.getString("db.default.password")

flywayLocations := Seq("filesystem:conf/db/migration")

flywayTarget := "4.0.1"

flywayBaselineVersion := "1.0.0"
