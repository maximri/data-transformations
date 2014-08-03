package parser

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

/**
 * Created by maximribakov on 7/31/14.
 */
class NSCAParserLearningTest extends SpecificationWithJUnit {

    trait Context extends Scope {
      val parser = new NSCAParser
      val records = SampleNCSALogData.data

    }

    "NSCAParserTest" should {

      "parse one record successfully" in new Context {
        val record = parser.parseRecord(records(0))

        record.get.ip === "24.185.144.104"
        record.get.clientIdentity === "-"
        record.get.authUser === "-"
        record.get.dateTime === "[18/Jul/2014:00:00:02 +0000]"
        record.get.request === "GET /_api/dynamicmodel HTTP/1.1"
        record.get.httpStatusCode === "200"
        record.get.bytesSent === "12988"
        record.get.refererURL === "http://www.heavenberry.com/"
        record.get.userAgent === "Mozilla/5.0 (Linux; Android 4.4.2; SM-G900V Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.141 Mobile Safari/537.36"
      }

      //38.123.140.114 -  -  [18/Jul/2014:00:00:03 +0000] "GET /cgi-bin/wspd_cgi.sh/WService=wsbroker1/webtools/oscommand.w HTTP/1.1" 301 0 "-" "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)"

      "parse one different record successfully" in new Context {
        val record = parser.parseRecord(records(1))

        record.get.ip === "38.123.140.114"
        record.get.clientIdentity == "-"
        record.get.authUser == "-"
        record.get.dateTime == "[18/Jul/2014:00:00:03 +0000]"
        record.get.request == "GET /cgi-bin/wspd_cgi.sh/WService=wsbroker1/webtools/oscommand.w HTTP/1.1"
        record.get.httpStatusCode == "301"
        record.get.bytesSent == "0"
        record.get.refererURL == "-"
        record.get.userAgent == "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)"

      }
    }
  }
