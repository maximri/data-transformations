package queries

import domain.RequestStringParameters

/**
 * Created by maximribakov on 7/31/14.
 */
case class DataTransformations(requests: List[Option[RequestStringParameters]]) {
  def getMostPopularUrls(numOfRecords: Int = 10) = {

    val emptyURLKey: String = "-"

    val groupByURL = (requests.groupBy(_.get.refererURL)) - emptyURLKey
    val groupByURLSorted = groupByURL.toList.sortBy(_._2.size)
    val popularURL = groupByURLSorted.takeRight(numOfRecords)
    popularURL.map(popularURL => {
      val urlTransformations: DataTransformations = DataTransformations(popularURL._2)
      PopularUrl(popularURL._1, urlTransformations.countAllRequest, urlTransformations.sizeUpAllRequestsSize, urlTransformations.countClientErrorRates)

    }).toSet
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
