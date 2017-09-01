import sbt._

/* List the dependencies specific to the service here */
object ServiceDependencies {
  val casbahVersion = "2.8.1"
  val mongoDriverVersion = "2.13.1"
  val salatVersion = "1.9.9"
  val slf4jApiVersion = "1.7.12"
  val slf4jApi = "org.slf4j" % "slf4j-api" % slf4jApiVersion
  val mockito = "org.mockito" % "mockito-core" % "2.8.9"
  val embedMongo = "com.github.simplyscala" %% "scalatest-embedmongo" % "0.2.4" % "test"

  val serviceDependencies : Seq[ModuleID] = Seq(
    "org.mongodb" %% "casbah" % casbahVersion,
    slf4jApi,
    "org.mongodb" % "mongo-java-driver" % mongoDriverVersion,
    "com.novus" %% "salat" % salatVersion,
    mockito,
    embedMongo
  )
}
