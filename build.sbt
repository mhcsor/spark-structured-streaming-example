name := "spark-structured-streaming-example"

version := "0.1"

scalaVersion := "2.11.12"

val AVRO_VERSION = "1.8.2"
val KAFKA_VERSION = "2.0.1"
val SCALA_ARM_VERSION = "2.0"
val SCALA_LOGGING_VERSION = "3.9.0"
val SCALA_TEST_VERSION = "3.0.4"
val SCALLOP_VERSION = "3.1.1"
val AVRO_SERIALIZER_VERSION = "4.0.0"
val JACKSON_VERSION = "2.9.4"
val SPARK_VERSION = "2.4.0"
val LIFT_VERSION = "3.2.0"

lazy val sparkSqlKafka = "org.apache.spark" %% "spark-sql-kafka-0-10" % SPARK_VERSION
lazy val sparkCore = "org.apache.spark" %% "spark-core" % SPARK_VERSION
lazy val sparkSql = "org.apache.spark" %% "spark-sql" % SPARK_VERSION
lazy val jacksonCore = "com.fasterxml.jackson.core" % "jackson-databind" % JACKSON_VERSION
lazy val jacksonScala = "com.fasterxml.jackson.module" %% "jackson-module-scala" % JACKSON_VERSION
lazy val avro = "org.apache.avro" % "avro" % AVRO_VERSION
lazy val avroSerializer = "io.confluent" % "kafka-avro-serializer" % AVRO_SERIALIZER_VERSION
lazy val avroStreamsSerializer = "io.confluent" % "kafka-streams-avro-serde" % AVRO_SERIALIZER_VERSION
lazy val kafkaClient = "org.apache.kafka" % "kafka-clients" % KAFKA_VERSION
lazy val kafkaStreams = "org.apache.kafka" % "kafka-streams" % KAFKA_VERSION
lazy val kafkaStreamsScala = "org.apache.kafka" %% "kafka-streams-scala" % KAFKA_VERSION
lazy val liftJson = "net.liftweb" %% "lift-json" % LIFT_VERSION
lazy val javaxRs = "javax.ws.rs" % "javax.ws.rs-api" % "2.1" artifacts( Artifact("javax.ws.rs-api", "jar", "jar"))
lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % SCALA_LOGGING_VERSION
lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies ++= Seq(
  sparkSqlKafka,
  sparkCore,
  sparkSql,
  jacksonCore,
  jacksonScala,
  kafkaClient,
  kafkaStreams,
  kafkaStreamsScala,
  liftJson,
  javaxRs,
  scalaLogging,
  logback
)
