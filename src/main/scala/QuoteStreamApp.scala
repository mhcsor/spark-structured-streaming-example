import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType


object QuoteStreamApp extends App {

  val spark = SparkSession
    .builder()
    .master("local[2]")
    .appName("Spark Streaming Example")
    .getOrCreate()

  val df = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", scala.util.Properties.envOrElse("KAFKA_BROKERS", "172.17.0.1:9092"))
    .option("subscribe", "quotes")
    .option("startingOffsets", "earliest")
    .load()

  import spark.implicits._

  val quoteType = new StructType()
    .add("ID", "integer")
    .add("title", "string")
    .add("content", "string")
    .add("link", "string")

  df
    .selectExpr("CAST(value AS STRING)")
    .select(from_json($"value", quoteType).as("quote"))
    .select("quote.ID", "quote.title", "quote.content", "quote.link")
    .withColumn("content", ltrim(lower(regexp_replace($"content", lit("<[^>]*>"), lit("")))))
    .withColumn("content", regexp_replace($"content", lit("[0-9*#+,;\\?\\.]"), lit("")))
    .withColumn("words", explode(split($"content", " ")))
    .groupBy($"words")
    .count()
    .writeStream
    .outputMode("update")
    .format("console")
    .start()
    .awaitTermination()

}
