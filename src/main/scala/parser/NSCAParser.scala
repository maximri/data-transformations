package parser

/**
 * Created by maximribakov on 7/30/14.
 */

import java.util.regex.{Matcher, Pattern}

import domain.Request

/**
 * A sample:
 * 24.185.144.104 -  -  [18/Jul/2014:00:00:02 +0000] "GET /_api/dynamicmodel HTTP/1.1" 200 12988 "http://www.heavenberry.com/"
 *                        "Mozilla/5.0 (Linux; Android 4.4.2; SM-G900V Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.141 Mobile Safari/537.36"
 */
class NSCAParser {
  // \\ - is an escape character in java, read the regex as if \\ = \

  private val ipPart = "\\d{1,3}"                               // at least 1 number but not more than 3 times

  private val ip = s"($ipPart\\.$ipPart\\.$ipPart\\.$ipPart)?"  // 123.456.7.89
  private val client = "(\\S+)"                                 // '\S' is 'non-whitespace character'
  private val user = "(\\S+)"
  private val date = "(\\[.+?\\])"                              // [21/Jul/2009:02:48:13 -0700]
  private val request = "\"(.*?)\""                             // any number of any character between quotes
  private val status = "(\\d{3})"
  private val bytes = "(\\S+)"                                  // this can be a "-"
  private val referer = "\"(.*?)\""
  private val agent = "\"(.*?)\""
  private val requestRegex = s"$ip $client  $user  $date $request $status $bytes $referer $agent"

  private val pattern = Pattern.compile(requestRegex)

  def parseRecord(record: String): Option[Request] = {
    val matcher = pattern.matcher(record)
    if (matcher.find) {
      Some(buildRequestRecord(matcher))
    } else {
      None
    }
  }

  def parseRecords(requestRecords: Array[String]):List[Request]  = {
    requestRecords.map(parseRecord(_)).filter(_!=None).map(_.get).toList
  }

  private def buildRequestRecord(matcher: Matcher) = {
    val ip: String = matcher.group(1)
    val client: String = matcher.group(2)
    val user: String = matcher.group(3)
    val date: String = matcher.group(4)
    val request: String = matcher.group(5)
    val status = matcher.group(6) match {
      case "-" => None
      case code => Some(code.toInt)
    }
    val bytes = matcher.group(7) match {
      case "-" => None
      case size => Some(size.toInt)
    }
    val referer: String = matcher.group(8)
    val agentData: String = matcher.group(9)

    Request(ip,client,user,date,request,status,bytes,referer,agentData)
  }
}
