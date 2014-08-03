package queries

import domain.RequestStringParameters
import org.apache.commons.lang.StringUtils

/**
 * Created by maximribakov on 7/31/14.
 */
case class DataTransformations(requests: List[Option[RequestStringParameters]]) {
  def getMostPopularIPSubnet(numOfPopularRecords: Int = 10) = {

    val groupByIPSubSetFunc: (Option[RequestStringParameters]) => String = req => {
      val secondIndexOfDot = StringUtils.ordinalIndexOf(req.get.ip, ".", 2)
      req.get.ip.substring(secondIndexOfDot + 1)
    }
    getMostPopularGroupResultSet(numOfPopularRecords, groupByIPSubSetFunc)
  }

  private def getMostPopularGroupResultSet(numOfPopularRecords: Int, groupByFunc: (Option[RequestStringParameters]) => String): Set[PopularRequestParam] = {
    val emptyParamKey: String = "-"
    val groupByParam = (requests.groupBy(groupByFunc)) - emptyParamKey

    val groupBySortedByResults = groupByParam.toList.sortBy(_._2.size)
    val popularParam = groupBySortedByResults.takeRight(numOfPopularRecords)

    popularParam.map(popularURL => {
      val dataTransOnParamRecords = DataTransformations(popularURL._2)

      PopularRequestParam(popularURL._1, dataTransOnParamRecords.countAllRequest,
                          dataTransOnParamRecords.sizeUpAllRequestsSize, dataTransOnParamRecords.countErrorRates)
    }).toSet
  }

  def getMostPopularUrls(numOfPopularRecords: Int = 10) = {

    val groupByURLFunc: (Option[RequestStringParameters]) => String = _.get.refererURL

    getMostPopularGroupResultSet(numOfPopularRecords, groupByURLFunc)

  }


  def countClientErrorRates = {
    val upperBound = 500
    val lowerBound = 399
    countErrorRateInRange(upperBound, lowerBound)
  }

  def countServerErrorRates = {
    val upperBound = 600
    val lowerBound = 499
    countErrorRateInRange(upperBound, lowerBound)
  }

  def countErrorRates = {
    val upperBound = 600
    val lowerBound = 399
    countErrorRateInRange(upperBound, lowerBound)
  }

  private def countErrorRateInRange(upperBound: Int, lowerBound: Int): Double = {
    val groupedByErrorRates = requests.map(_.get.httpStatusCode).groupBy(status => status.toInt > lowerBound && status.toInt < upperBound)
    val totalAmountOfErrors = groupedByErrorRates.getOrElse(true, List.empty).size
    totalAmountOfErrors.toDouble / countAllRequest
  }

  def sizeUpAllRequestsSize = requests.map(_.get.bytesSent).foldLeft(0)(_ + _.toInt)

  def countAllRequest = requests.size
}
