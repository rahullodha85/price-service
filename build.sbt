import CommonDependencies._
import net.virtualvoid.sbt.graph.Plugin._
import ServiceDependencies._
import scalariform.formatter.preferences._

name := """price-service"""

version := "0.1"

envVars := Map("PRIVATE_IP" -> "someServer")

val defaultSettings: Seq[Setting[_]] = Seq(
  scalaVersion  := "2.11.7",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature","-Ywarn-unused-import"),
  libraryDependencies ++= commonDependencies
) ++ graphSettings


lazy val root = (project in file("."))
  .settings(defaultSettings: _*)
  .settings(libraryDependencies ++= serviceDependencies)
  .enablePlugins(PlayScala)

libraryDependencies ~= { _.map(_.exclude("org.slf4j", "slf4j-log4j12")) }

resolvers ++= Seq("Saks Artifactory - Ext Release Local" at "http://repo.saksdirect.com:8081/artifactory/ext-release-local",
  "Saks Artifactory - Libs Release Local" at "http://repo.saksdirect.com:8081/artifactory/libs-release-local",
  "Saks Artifactory - Libs Release" at "http://repo.saksdirect.com:8081/artifactory/libs-release",
  "jitpack" at "https://jitpack.io",
  Resolver.jcenterRepo
)

lazy val buildAll = TaskKey[Unit]("build-all", "Compiles and runs all unit and integration tests.")
lazy val buildZip = TaskKey[Unit]("build-zip","Publishes a zip file with the new code.")

buildAll <<= Seq(clean, compile in Compile, compile in Test, test in Test).dependOn

buildZip <<= ((packageBin in Universal) map { out =>
  println("Copying Zip file to docker directory.")
  IO.move(out, file(out.getParent + "/../../docker/" + out.getName))
}).dependsOn(buildAll)


scalariformSettings

ScalariformKeys.preferences := FormattingPreferences()
  .setPreference( AlignParameters, true )
  .setPreference( AlignSingleLineCaseStatements, true )
  .setPreference( DoubleIndentClassDeclaration, true )
