name := """MovieReviewer"""
version := "0.1.0-SNAPSHOT"
lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"
incOptions := incOptions.value.withNameHashing(true)
updateOptions := updateOptions.value.withCachedResolution(cachedResoluton = true)

libraryDependencies ++= Seq(
  cache,

  "org.sorm-framework" % "sorm" % "0.3.20",
  "com.h2database" % "h2" % "1.4.192",
  "org.slf4j" % "slf4j-simple" % "1.7.21",
  "io.circe" %% "circe-core" % "0.8.0",
  "io.circe" %% "circe-generic" % "0.8.0",
  "io.circe" %% "circe-parser" % "0.8.0",

  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test"

)

resolvers ++= Seq(
  "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo" at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging" at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "Twitter Repository" at "http://maven.twttr.com",
  "Madoushi sbt-plugins" at "https://dl.bintray.com/madoushi/sbt-plugins/",
  Resolver.bintrayRepo("websudos", "oss-releases")
)

dependencyOverrides += "org.webjars.npm" % "minimatch" % "2.0.10"

javaOptions += "-org.slf4j.simpleLogger.log.sorm=debug"
// the typescript typing information is by convention in the typings directory
// It provides ES6 implementations. This is required when compiling to ES5.

routesGenerator := InjectedRoutesGenerator
