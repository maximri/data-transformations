package queries

import domain.Request
import org.apache.commons.lang.StringUtils

/**
 * Created by maximribakov on 7/31/14.
 */
case class DataTransformations(requests: List[Request]) {
  def getTheMostErroneousPath = {
    //    val groupedByErrorRates = requests.filter(_.httpStatusCode.get!=None).groupBy(request=>numInRangeFunc(600, 399, request.httpStatusCode.get) )
    //    //println("groupedByErrorRates = " + groupedByErrorRates)
    //
    //    val requestWithErrorGroupByPath = groupedByErrorRates(true).groupBy(request=> request.refererURL) - "-"
    //
    //   // println("requestWithErrorGroupByPath = " + requestWithErrorGroupByPath)
    //
    //    val requestWithErrorGroupByPathSorted = requestWithErrorGroupByPath.toList.sortBy(_._2.size)
    //
    //    val mostEroneousPathReqests = requestWithErrorGroupByPathSorted.head
    //
    //   println("mostEroneousPathReqests = " + mostEroneousPathReqests)
    //
    Map()

  }

  def getMostPopularIPSubnet(numOfPopularRecords: Int = 10) = {

    val groupByIPSubSetFunc: (Request => String) =
      req => {
        val secondIndexOfDot = StringUtils.ordinalIndexOf(req.ip, ".", 2)
        req.ip.substring(secondIndexOfDot + 1)
      }

    getMostPopularGroupResultSet(numOfPopularRecords, groupByIPSubSetFunc)
  }

  def getMostPopularUrls(numOfPopularRecords: Int = 10) = getMostPopularGroupResultSet(numOfPopularRecords, _.refererURL)

  def countClientErrorRates = countErrorRateByFunc(numInRangeFunc(upperBound = 500, lowerBound = 399))

  def countServerErrorRates = countErrorRateByFunc(numInRangeFunc(upperBound = 600, lowerBound = 499))

  def countErrorRates = countErrorRateByFunc(numInRangeFunc(upperBound = 600, lowerBound = 399), isEmptyStatusCodeConsidered = true)

  def sizeUpAllRequestsSize = requests.map(_.bytesSent.getOrElse(0)).foldLeft(0)(_ + _)

  def countAllRequest = requests.size

  private def countErrorRateByFunc(func: Int => Boolean, isEmptyStatusCodeConsidered: Boolean = false): Double = {
    val totalAmountOfErrors = requests.count(request => request.httpStatusCode match {
      case None => isEmptyStatusCodeConsidered
      case Some(code) => func(code)
    })

    totalAmountOfErrors.toDouble / countAllRequest
  }

  private def numInRangeFunc(upperBound: Int, lowerBound: Int): (Int => Boolean) = {
    num => num > lowerBound && num < upperBound
  }

  private def getMostPopularGroupResultSet(numOfPopularRecords: Int, groupByFunc: Request => String,prioritizeGroupByFunc:  ((String, List[Request]))=>Int): Set[PopularRequestParam] = {

    val getLargestGroup: ((String, List[Request])) => Int = groupedRequest => groupedRequest._2.size

    val emptyParamKey: String = "-"
    val groupByParam = (requests.groupBy(groupByFunc)) - emptyParamKey



    val groupBySortedByPopularity = groupByParam.toList.sortBy(getLargestGroup)

    val mostPopular = groupBySortedByPopularity.takeRight(numOfPopularRecords)

    mostPopular.map(popularParam => {
      val popularParamRecords = popularParam._2
      val dataTransOnParamRecords = DataTransformations(popularParamRecords)

      PopularRequestParam(popularParam._1, dataTransOnParamRecords.countAllRequest,
        dataTransOnParamRecords.sizeUpAllRequestsSize, dataTransOnParamRecords.countErrorRates)
    }).toSet
  }
}
