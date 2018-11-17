import java.util.Properties
import java.util.concurrent.TimeUnit

import QuoteApiClient._
import com.typesafe.scalalogging.Logger
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

import scala.concurrent.Promise


object QuoteKafkaProducer extends App {

  val logger = Logger("QuoteKafkaProducer")


  def createKafkaProducer: KafkaProducer[Int, Quote] = {
    val properties = new Properties()
    properties.put("bootstrap.servers", scala.util.Properties.envOrElse("KAFKA_BROKERS", "172.17.0.1:9092"))
    properties.put("client.id", "random-citation-producer")
    properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer")
    properties.put("value.serializer", "QuoteSerializer")
    properties.put("producer.type", "async")

    new KafkaProducer[Int, Quote](properties)
  }

  val producer = createKafkaProducer

  for (quote <- Stream.continually(generateQuote)) {
    val message = quote.getOrElse(Quote(-1, "Dummy quote", "Dummy content", "dummy-link"))
    val p = Promise[(RecordMetadata, Exception)]()

    producer.send(new ProducerRecord[Int, Quote]("quotes", message.ID, message), new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        p.success((metadata, exception))
      }
    })

    TimeUnit.MILLISECONDS.sleep(150)

  }
}
