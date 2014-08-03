package queries

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope
import parser.{NSCAParser, SampleNCSALogData}

/**
 * Created by maximribakov on 7/31/14.
 */
class DataTransformationsTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val parser = new NSCAParser
    val records = parser.parseRecords(SampleNCSALogData.dataLong)
  //  val dataTrasformation = DataTransformations(records)
  }

  "DataTransformation" should {

    "count all records" in new Context {
      DataTransformations(records.take(4)).countAllRequest === 4
      DataTransformations(records.take(3)).countAllRequest === 3
    }

    "size up all of the requests size" in new Context {
      DataTransformations(records.take(1)).sizeUpAllRequestsSize === 12988
      DataTransformations(records.take(3)).sizeUpAllRequestsSize === (12988 + 0 + 27012)
    }

    "count error rate for all request" in new Context {
      DataTransformations(records.take(1)).countErrorRates === 1
      DataTransformations(records.take(4)).countErrorRates === 2.0/4
    }

    "count client error rate" in new Context {
      DataTransformations(records.take(1)).countClientErrorRates === 1
      DataTransformations(records.take(4)).countClientErrorRates === 1.0/4
      DataTransformations(records.take(13)).countClientErrorRates === 2.0/13
    }


    "count server error rate" in new Context {
      DataTransformations(records.take(1)).countServerErrorRates === 0
      DataTransformations(records.take(4)).countServerErrorRates === 1.0/4
      DataTransformations(records.take(13)).countServerErrorRates === 1.0/13
    }

    "find top 10 popular URL" in new Context {
      DataTransformations(records.take(4)).getMostPopularUrls(1) === Set(PopularUrl("http://www.heavenberry.com/",requestCount = 2, responseSize=(38918+12988),errorRate=(2.0/4)))
      DataTransformations(records.take(4)).getMostPopularUrls() === Set(PopularUrl("http://www.heavenberry.com/",requestCount = 2, responseSize=(38918+12988),errorRate=(2.0/4)))
      DataTransformations(records.take(9)).getMostPopularUrls(2) ===
        Set(PopularUrl("http://www.stumbleupon.com/refer.php?url=http%3A%2F%2Fwww.towerinvestment.org%2F",
                       requestCount = 1,responseSize = 33528,errorRate = 0),
           PopularUrl("http://www.heavenberry.com/",requestCount = 2, responseSize=(38918+12988),errorRate=(2.0/4)))

    }
  }
}
