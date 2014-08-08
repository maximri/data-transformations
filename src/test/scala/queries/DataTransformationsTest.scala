

package queries

import domain.Request
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope
import parser.{NSCAParser, SampleNCSALogData}

/**
 * Created by maximribakov on 7/31/14.
 */
class DataTransformationsTest extends SpecificationWithJUnit {

  trait Context extends Scope {

    val OK: Some[Int] = Some(200)
    val CLIENT_ERROR: Some[Int] = Some(400)
    val SERVER_ERROR: Some[Int] = Some(500)
    //TODO remove after fixing rest of the tests
    val parser = new NSCAParser
    val records = parser.parseRecords(SampleNCSALogData.dataLong)

    val recordsWith1EmptyRequest = List(Request())
    val recordsWith3Requests = List(Request(), Request(), Request())
  }

  "DataTransformation" should {

    "count all records" in new Context {
      DataTransformations(recordsWith1EmptyRequest).countAllRequest === 1
      DataTransformations(recordsWith3Requests).countAllRequest === 3
    }

    "size up all of the requests size when all just one records" in new Context {
      DataTransformations(List(Request(bytesSent = Some(100)))).sizeUpAllRequestsSize === 100
      DataTransformations(recordsWith1EmptyRequest).sizeUpAllRequestsSize === 0
    }

    "size up all of the requests size when multiple records" in new Context {
      DataTransformations(List(
        Request(bytesSent = Some(129)),
        Request(),
        Request(bytesSent = Some(27)))
      ).sizeUpAllRequestsSize === 129 + 0 + 27
    }

    "count error rate for one request when httpStatus code OK" in new Context {
      DataTransformations(List(Request(httpStatusCode = OK))).countErrorRates === 0 / 1
    }

    "count error rate for one request when httpStatus is a client error" in new Context {
      DataTransformations(List(Request(httpStatusCode = CLIENT_ERROR))).countErrorRates === 1 / 1
    }
    
    "count error rate for one request when httpStatus is a server error" in new Context {
      DataTransformations(List(Request(httpStatusCode = SERVER_ERROR))).countErrorRates === 1 / 1
    }

    "count error rate for one request when httpStatus is not defined" in new Context {
       DataTransformations(List(Request())).countErrorRates === 1 / 1
    }

    "count error rate for multiple requests" in new Context {
      DataTransformations(List(
        Request(httpStatusCode = CLIENT_ERROR),
        Request(httpStatusCode = SERVER_ERROR),
        Request(httpStatusCode = OK),
        Request())).countErrorRates === 3.0 / 4
    }

    "count client error rate for one request when httpStatus code OK" in new Context {
      DataTransformations(List(Request(httpStatusCode = OK))).countClientErrorRates === 0 / 1
    }

    "count client error rate for one request when httpStatus is a client error" in new Context {
      DataTransformations(List(Request(httpStatusCode = CLIENT_ERROR))).countClientErrorRates === 1 / 1
    }

    "count client error rate for one request when httpStatus is a server error" in new Context {
      DataTransformations(List(Request(httpStatusCode = SERVER_ERROR))).countClientErrorRates === 0 / 1
    }

    "count client error rate for one request when httpStatus is not defined" in new Context {
      DataTransformations(List(Request())).countClientErrorRates === 0 / 1
    }

    "count client error rate for multiple requests" in new Context {
      DataTransformations(List(
        Request(httpStatusCode = CLIENT_ERROR),
        Request(httpStatusCode = SERVER_ERROR),
        Request(httpStatusCode = OK),
        Request())).countClientErrorRates === 1.0 / 4
    }


    "find top 10 popular URL" in new Context {
      // TODO remove parser coupling
      DataTransformations(records.take(4)).getMostPopularUrls(1) ===
        Set(PopularRequestParam("http://www.heavenberry.com/", requestCount = 2, responseSize = (38918 + 12988), errorRate = (1.0 / 2)))
      DataTransformations(records.take(4)).getMostPopularUrls() ===
        Set(PopularRequestParam("http://www.heavenberry.com/", requestCount = 2, responseSize = (38918 + 12988), errorRate = (1.0 / 2)))
      DataTransformations(records.take(9)).getMostPopularUrls(2) ===
        Set(PopularRequestParam("http://www.stumbleupon.com/refer.php?url=http%3A%2F%2Fwww.towerinvestment.org%2F",
          requestCount = 1, responseSize = 33528, errorRate = 0),
          PopularRequestParam("http://www.heavenberry.com/", requestCount = 2, responseSize = (38918 + 12988), errorRate = (2.0 / 4)))

    }

    "find top 10 popular IP subnet " in new Context {
      DataTransformations(records.take(4)).getMostPopularIPSubnet(1) === Set(PopularRequestParam("144.104", requestCount = 2, responseSize = (27012 + 12988), errorRate = (2.0 / 2)))
      DataTransformations(records.take(4)).getMostPopularIPSubnet() ===
        Set(PopularRequestParam("144.104", requestCount = 2, responseSize = (27012 + 12988), errorRate = (2.0 / 2)),
          PopularRequestParam("140.114", requestCount = 1, responseSize = 0, errorRate = 0),
          PopularRequestParam("177.77", requestCount = 1, responseSize = 38918, errorRate = 0))

    }

    "Find the most erroneous path; for each error type for this path, count the number of requests" in new Context {
      // DataTransformations(records.take(1)).getTheMostErroneousPath === Map(400->1)
      //  DataTransformations(records.take(4)).getTheMostErroneousPath === Map(400->1)
    }
  }
}
