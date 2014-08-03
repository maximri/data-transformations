package queries

import domain.RequestStringParameters

/**
 * Created by maximribakov on 7/31/14.
 */
case class DataTransformations(requests: List[Option[RequestStringParameters]]) {

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
    val totalAmountOfErrors = groupedByErrorRates.getOrElse(true,List.empty).size
    totalAmountOfErrors.toDouble / countAllRequest
  }

  def sizeUpAllRequestsSize = requests.map(_.get.bytesSent).foldLeft(0)(_ + _.toInt)

  def countAllRequest = requests.size
}
