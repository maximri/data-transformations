package domain

/**
 * Created by maximribakov on 7/30/14.
 */
case class RequestRecord(ip: Option[IP] = None, ident: Option[String] = None, authuser: Option[String] = None, date: Option[String] = None,
                         request: Option[String] = None, status: Option[Int] = None, bytes: Option[Int] = None, url: Option[String] = None,
                         userAgent: Option[String] = None)

case class IP(a: Int = 127, b: Int = 0, c: Int = 0, d: Int = 1)
