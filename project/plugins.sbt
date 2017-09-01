scalacOptions ++= Seq("-deprecation", "-language:_", "-unchecked")

// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.4")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "3.0.0")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.6.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.0")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.4.0")


//had to do this as the dbSetupMockCADPrices build task needs them for setting up mongo.
libraryDependencies ++= Seq(
  "org.mongodb" %% "casbah" % "2.8.1",
  "org.mongodb" % "mongo-java-driver" % "2.13.1"
)
