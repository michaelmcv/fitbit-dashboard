lazy val apiDependencies = Seq(
     "com.stackmob" %% "newman" % "1.3.5",
     "com.google.apis" % "google-api-services-drive" % "v2-rev168-1.20.0"
 )

lazy val webDependencies = Seq(
     "net.databinder" %% "unfiltered-filter" % "0.8.4",
     "net.databinder" %% "unfiltered-jetty" % "0.8.4",
     "org.scalatra.scalate" %% "scalate-core" % "1.7.0",
     "org.anormcypher" %% "anormcypher" % "0.6.0",
     "com.github.nscala-time" %% "nscala-time" % "2.0.0",
     "org.slf4j" % "slf4j-simple" % "1.6.2"
)

lazy val commonSettings = Seq(
  organization := "com.m-mcveigh",
  version := "0.1.0"
)

lazy val baseScalaVersion = Seq (
  scalaVersion := "2.10.5"
)

lazy val webScalaVersion = Seq (
  scalaVersion := "2.11.2"
)

lazy val serviceSettings = baseScalaVersion ++ commonSettings ++ (libraryDependencies ++= apiDependencies)
lazy val webSettings = webScalaVersion ++ commonSettings ++ (libraryDependencies ++= webDependencies)

lazy val config = (project in file("config")).
  settings(commonSettings: _*).
  settings(
    name := "config"
 )

lazy val extractor = (project in file("extractor")).
  settings(serviceSettings: _*).
  settings(
    name := "extractor"
 ).dependsOn(config)

lazy val loader = (project in file("loader")).
  settings(serviceSettings: _*).
  settings(
    name := "loader"
 ).dependsOn(config)

 lazy val dashboard = (project in file("dashboard")).
   settings(webSettings: _*).
   settings(
     name := "dashboard",
     resolvers ++= Seq( "anormcypher" at "http://repo.anormcypher.org/",
                        "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"))

lazy val fitbitActivitySummary = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "fitbit-activity-summary"
 )