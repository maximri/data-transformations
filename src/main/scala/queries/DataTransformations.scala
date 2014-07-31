package queries

import domain.RequestStringParameters

/**
 * Created by maximribakov on 7/31/14.
 */
case class DataTransformations(requests : List[Option[RequestStringParameters]]) {
  def countAllRequest = 4
}
