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
  }
}
