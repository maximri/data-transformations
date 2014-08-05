package general

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope
import parser.{NSCAParser, SampleNCSALogData}
import queries.DataTransformations

/**
 * Created by maximribakov on 8/5/14.
 */
class DataTransformationEndToEndTest extends SpecificationWithJUnit {

  trait Context extends Scope {
    val parser = new NSCAParser
    val records = parser.parseRecords(SampleNCSALogData.dataLong)
  }

  "DataTransformation" should {

    "count all records" in new Context {
      DataTransformations(records.take(4)).countAllRequest === 4
    }
  }
}
