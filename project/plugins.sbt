logLevel := Level.Info

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.3")

addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.5.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")

addSbtPlugin("org.lyranthe.sbt" % "partial-unification" % "1.1.0")

libraryDependencies += "mysql" % "mysql-connector-java"  % "5.1.48"
addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "3.3.+")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.3")

addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")
resolvers += "Flyway" at "https://flywaydb.org/repo"

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.4.0")
