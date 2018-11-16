import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.scalalogging.Logger
import org.apache.kafka.common.serialization.Serializer

case class Quote(ID: Int, title: String, content: String, link: String)

class QuoteSerializer extends Serializer[Quote] {
  val mapper = new ObjectMapper()
  val logger = Logger(classOf[QuoteSerializer])

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
    mapper.registerModule(DefaultScalaModule)
  }

  override def serialize(topic: String, data: Quote): Array[Byte] = {
    val bytes = mapper.writeValueAsBytes(data)

    logger.info((bytes.map(_.toChar)).mkString)

    bytes
  }

  override def close(): Unit = {

  }
}
