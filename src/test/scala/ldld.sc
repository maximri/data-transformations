import org.apache.commons.lang.StringUtils

val req ="127.0.0.1"
val indexOf: Int = req.indexOf(".", 0)
req.substring(req.indexOf(".", indexOf+2))

StringUtils.ordinalIndexOf("aabaabaa", "b", 2)  == 5