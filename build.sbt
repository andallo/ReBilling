name := """rebilling"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

resolvers += (
  "Local Maven Repository" at "file://Users/andrey/.m2/repository"
  )

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mongodb.morphia" % "morphia" % "0.107",
  "org.mongodb.morphia" % "morphia-logging-slf4j" % "0.107",
  "com.pdfcrowd" % "pdfcrowd" % "2.6",
  "junit" % "junit" % "4.11"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
