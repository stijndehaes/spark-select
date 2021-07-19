name := "spark-select"

organization := "io.minio"

scalaVersion := "2.12.10"

//spName := "minio/spark-select"

//spAppendScalaVersion := true

//spIncludeMaven := true

//spIgnoreProvided := true

val sparkVersion = "3.1.2"

// used spark components
//sparkComponents := Seq("sql")

assemblyMergeStrategy in assembly := {
  case "META-INF/io.netty.versions.properties" => MergeStrategy.concat
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

// Dependent libraries
libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk" % "1.11.434" exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.apache.commons" % "commons-csv" % "1.7",
  "org.slf4j" % "slf4j-api" % "1.7.5" % "provided",
  "org.mockito" % "mockito-core" % "2.0.31-beta"
)

libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion % "provided"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "test" force(),
  "org.apache.spark" %% "spark-sql" % sparkVersion % "test"  force(),
  "org.scala-lang" % "scala-library" % scalaVersion.value % "compile"
)

/**
 * release settings
  */

publishMavenStyle := true

releaseCrossBuild := true

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

releasePublishArtifactsAction := PgpKeys.publishSigned.value

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <url>https://github.com/minio/spark-select</url>
    <scm>
      <connection>scm:git:github.com/minio/spark-select</connection>
      <developerConnection>scm:git:git@github.com:minio/spark-select</developerConnection>
      <url>github.com/minio/spark-select</url>
    </scm>
    <developers>
      <developer>
        <id>minio</id>
        <name>MinIO</name>
        <url>http://www.minio.io</url>
      </developer>
    </developers>)

// Skip tests during assembly
test in assembly := {}

/*
ScoverageSbtPlugin.ScoverageKeys.coverageHighlighting := {
  if (scalaBinaryVersion.value == "2.10") false
  else true
}
*/

// Add publishing to spark packages as another step.
/*releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  pushChanges,
  releaseStepTask(spPublish)
)*/
