package domain

/**
 * Created by maximribakov on 7/30/14.
 */
case class RequestStringParameters (
                             ip: String,
                             clientIdentity: String,          // typically `-`
                             authUser: String,                // typically `-`
                             dateTime: String,                // [day/month/year:hour:minute:second zone]
                             request: String,                 // `GET /foo ...`
                             httpStatusCode: String,          // 200, 404, etc.
                             bytesSent: String,               // may be `-`
                             refererURL: String,                 // where the visitor came from
                             userAgent: String                // long string to represent the browser and OS
                             )