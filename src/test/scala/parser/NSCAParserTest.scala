package parser

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

/**
 * Created by maximribakov on 7/31/14.
 */
class NSCAParserTest extends SpecificationWithJUnit {

    trait Context extends Scope {
      val parser = new AccessLogParser
      val records = SampleNCSALogData.data

    }

    "NSCAParserTest" should {

      "parse one record successfully" in new Context {
        val record = parser.parseRecord(records(0))

        record.get.ip === "124.30.9.161"
        record.get.clientIdentity === "-"
        record.get.authUser === "-"
        record.get.dateTime === "[21/Jul/2009:02:48:11 -0700]"
        record.get.request === "GET /java/edu/pj/pj010004/pj010004.shtml HTTP/1.1"
        record.get.httpStatusCode === "200"
        record.get.bytesSent === "16731"
        record.get.referer === "http://www.google.co.in/search?hl=en&client=firefox-a&rlz=1R1GGGL_en___IN337&hs=F0W&q=reading+data+from+file+in+java&btnG=Search&meta=&aq=0&oq=reading+data+"
        record.get.userAgent === "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5"
      }
      
      "parse one different record successfully" in new Context {
        val record = parser.parseRecord(records(1))

        record.get.ip === "89.166.165.223"
        record.get.clientIdentity == "-"
        record.get.authUser == "-"
        record.get.dateTime == "[21/Jul/2009:02:48:12 -0700]"
        record.get.request == "GET /favicon.ico HTTP/1.1"
        record.get.httpStatusCode == "404"
        record.get.bytesSent == "970"
        record.get.referer == "-"
        record.get.userAgent == "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11"

      }
    }
  }
