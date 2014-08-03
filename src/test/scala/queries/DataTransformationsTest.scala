package queries

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope
import parser.{NSCAParser, SampleNCSALogData}

/**
 * Created by maximribakov on 7/31/14.
 */
class DataTransformationsTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val parser = new NSCAParser
    val records = parser.parseRecords(SampleNCSALogData.dataLong)
  //  val dataTrasformation = DataTransformations(records)
  }

  "DataTransformation" should {

    "count all records" in new Context {
      DataTransformations(records.take(4)).countAllRequest === 4
      DataTransformations(records.take(3)).countAllRequest === 3
    }

    "size up all of the requests size" in new Context {
      DataTransformations(records.take(1)).sizeUpAllRequestsSize === 12988
      DataTransformations(records.take(3)).sizeUpAllRequestsSize === (12988 + 0 + 27012)
    }

    "count error rate for all request" in new Context {
      DataTransformations(records.take(1)).countErrorRates === 1
      DataTransformations(records.take(4)).countErrorRates === 2.0/4
    }

    "count client error rate" in new Context {
      DataTransformations(records.take(1)).countClientErrorRates === 1
      DataTransformations(records.take(4)).countClientErrorRates === 1.0/4
      DataTransformations(records.take(13)).countClientErrorRates === 2.0/13
    }


    "count server error rate" in new Context {
      DataTransformations(records.take(1)).countServerErrorRates === 0
      DataTransformations(records.take(4)).countServerErrorRates === 1.0/4
      DataTransformations(records.take(13)).countServerErrorRates === 1.0/13
    }
  }
}
