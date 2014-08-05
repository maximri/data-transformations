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
    //TODO remove after fixing rest of the tests
    val parser = new NSCAParser
    val records = parser.parseRecords(SampleNCSALogData.dataLong)

    val recordsWith1EmptyRequest = List(Request())
    val recordsWith3Requests = List(Request(), Request(), Request())
    val recordsWith4Requests = recordsWith3Requests :+ Request()

    val recordsWith1RequestWithSize = List(Request(bytesSent = Some(12988)))
    val recordsWith3RequestWithSize = List(Request(bytesSent = Some(12988)), Request(bytesSent = Some(123)), Request(bytesSent = Some(27012)))

    val recordsWith3RequestWithSizeDefinedForJust2 = List(Request(bytesSent = Some(12988)), Request(), Request(bytesSent = Some(27012)))

    val recordsWith1RequestWithSuccessfulHttpStatusCode = List(Request(httpStatusCode = Some(200)))
    val recordsWith1RequestWithClientErrorHttpStatusCode = List(Request(httpStatusCode = Some(400)))
    val recordsWith1RequestWithErroneousHttpStatusCode = recordsWith1RequestWithClientErrorHttpStatusCode
    val recordsWith1RequestWithServerErrorHttpStatusCode = List(Request(httpStatusCode = Some(500)))

    val recordsWith_4_RequestWith_3_ErroneousHttpStatusAnd_1_Successful =
      List(Request(httpStatusCode = Some(400)),Request(httpStatusCode = Some(400)),
        Request(httpStatusCode = Some(500)),Request(httpStatusCode = Some(200)))

    // TODO reconsider adding builder for records, this variable name is far too long
    val recordsWith_5_RequestWhich_1_HasClientErrorHttpStatusAnd_1_HasUndefinedHttpStatusAnd_2_SuccessfulHttpStatusAnd_1_HasServerStatusError =
      List(Request(httpStatusCode = Some(400)),Request(httpStatusCode = Some(201)),
        Request(httpStatusCode = Some(200)),Request(httpStatusCode = Some(500)),Request())

  }

  "DataTransformation" should {

    "count all records" in new Context {
      DataTransformations(recordsWith3Requests).countAllRequest === recordsWith3Requests.size
      DataTransformations(recordsWith4Requests).countAllRequest === recordsWith4Requests.size
    }

    "size up all of the requests size when all records have defined bytesSent(size)" in new Context {
      DataTransformations(recordsWith1RequestWithSize).sizeUpAllRequestsSize === recordsWith1RequestWithSize.head.bytesSent.getOrElse(0)
      DataTransformations(recordsWith3RequestWithSize).sizeUpAllRequestsSize ===
        recordsWith3RequestWithSize(0).bytesSent.getOrElse(0) +
        recordsWith3RequestWithSize(1).bytesSent.getOrElse(0) +
        recordsWith3RequestWithSize(2).bytesSent.getOrElse(0)
    }

    "size up all of the requests size when some records don't have defined bytesSent(size)" in new Context {
      DataTransformations(recordsWith1EmptyRequest).sizeUpAllRequestsSize === recordsWith1EmptyRequest.head.bytesSent.getOrElse(0)
      DataTransformations(recordsWith3RequestWithSizeDefinedForJust2).sizeUpAllRequestsSize ===
        recordsWith3RequestWithSizeDefinedForJust2(0).bytesSent.getOrElse(0) +
        recordsWith3RequestWithSizeDefinedForJust2(1).bytesSent.getOrElse(0) +
        recordsWith3RequestWithSizeDefinedForJust2(2).bytesSent.getOrElse(0)
    }

    "count error rate for all request when all of the httpStatusCode are Defined" in new Context {
      DataTransformations(recordsWith1RequestWithSuccessfulHttpStatusCode).countErrorRates === 0/1
      DataTransformations(recordsWith1RequestWithErroneousHttpStatusCode).countErrorRates === 1.0/1
      DataTransformations(recordsWith_4_RequestWith_3_ErroneousHttpStatusAnd_1_Successful).countErrorRates === 3.0/4
    }

    "count error rate for all request when not all of the httpStatusCode are Defined" in new Context {
      DataTransformations(recordsWith1EmptyRequest).countErrorRates === 1.0/1
      DataTransformations(recordsWith_5_RequestWhich_1_HasClientErrorHttpStatusAnd_1_HasUndefinedHttpStatusAnd_2_SuccessfulHttpStatusAnd_1_HasServerStatusError).countErrorRates === 3.0 / 5
    }

    "count client error rate for all request when none is an client httpStatus error code" in new Context {
      DataTransformations(recordsWith1EmptyRequest).countClientErrorRates === 0/1
      DataTransformations(recordsWith1RequestWithServerErrorHttpStatusCode).countClientErrorRates === 0 / 1
      DataTransformations(recordsWith1RequestWithSuccessfulHttpStatusCode).countClientErrorRates === 0 / 1
    }

    "count client error rate for all request when some have client httpStatus error code" in new Context {
      DataTransformations(recordsWith1RequestWithClientErrorHttpStatusCode).countClientErrorRates === 1.0 / 1
      DataTransformations(recordsWith1RequestWithClientErrorHttpStatusCode).countClientErrorRates === 1.0 / 1
      DataTransformations(recordsWith_5_RequestWhich_1_HasClientErrorHttpStatusAnd_1_HasUndefinedHttpStatusAnd_2_SuccessfulHttpStatusAnd_1_HasServerStatusError).countClientErrorRates === 1.0 / 5
    }

    "count server error rate" in new Context {
      DataTransformations(recordsWith_5_RequestWhich_1_HasClientErrorHttpStatusAnd_1_HasUndefinedHttpStatusAnd_2_SuccessfulHttpStatusAnd_1_HasServerStatusError).countServerErrorRates === 1.0 / 5
    }



    "find top 10 popular URL" in new Context {
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
