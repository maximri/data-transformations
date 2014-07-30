import scala.util.parsing.combinator._

object IPParser extends RegexParsers{
  def separator : Parser[String] = """\.""".r
  def ipPart : Parser[Int] = """[0-9]+""".r  ^^  {_.toInt}
  def ip : Parser[List[Int]] = repsep(ipPart, separator)

  def apply(input: String) = parseAll(ip, input)
}
IPParser("127.0.0.9")