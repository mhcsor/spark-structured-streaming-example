import com.typesafe.scalalogging.Logger
import net.liftweb.json.{DefaultFormats, parse}

object QuoteApiClient {

  val logger = Logger("QuoteApiClient")

  implicit val formats = DefaultFormats

  def generateQuote: Option[Quote] = {
    logger.info("Retrieving quote from [quotesondesign] API...")
    val url = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=1"
    parse(scala.io.Source.fromURL(url).mkString).extract[List[Quote]].lift(0)
  }

}
