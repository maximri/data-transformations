package parser

object SampleNCSALogData {

  val data = """
24.185.144.104 -  -  [18/Jul/2014:00:00:02 +0000] "GET /_api/dynamicmodel HTTP/1.1" 200 12988 "http://www.heavenberry.com/" "Mozilla/5.0 (Linux; Android 4.4.2; SM-G900V Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.141 Mobile Safari/537.36"
38.123.140.114 -  -  [18/Jul/2014:00:00:03 +0000] "GET /cgi-bin/wspd_cgi.sh/WService=wsbroker1/webtools/oscommand.w HTTP/1.1" 301 0 "-" "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)"
173.252.112.112 -  -  [18/Jul/2014:00:00:04 +0000] "GET /?_escaped_fragment_=shoulders-video%2Fc1gaz HTTP/1.1" 200 27012 "-" "facebookexternalhit/1.1 (+http://www.facebook.com/externalhit_uatext.php)"
203.185.177.77 -  -  [18/Jul/2014:00:00:05 +0000] "GET / HTTP/1.1" 200 38918 "-" "Mozilla/5.0 (Linux; Android 4.1.1; CINK+ Build/JRO03C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.141 Mobile Safari/537.36"
173.252.120.113 -  -  [18/Jul/2014:00:00:06 +0000] "GET /?from_fb=1&fb_locale=ja_JP HTTP/1.1" 200 26925 "-" "facebookexternalhit/1.1 (+http://www.facebook.com/externalhit_uatext.php)"
107.178.200.198 -  -  [18/Jul/2014:00:00:07 +0000] "GET /?gclid=CITKiYLHzb8CFRJk7AodWAIAeg HTTP/1.1" 200 58155 "-" "AppEngine-Google; (+http://code.google.com/appengine; appid: s~wixarchive2)"
107.178.200.206 -  -  [18/Jul/2014:00:00:08 +0000] "GET / HTTP/1.1" 200 30980 "-" "AppEngine-Google; (+http://code.google.com/appengine; appid: s~wixarchive2)"
95.18.87.85 -  -  [18/Jul/2014:00:00:10 +0000] "GET /noflashhtml HTTP/1.1" 200 2225 "http://www.anastaffing.com/" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36"
121.214.157.146 -  -  [18/Jul/2014:00:00:11 +0000] "GET / HTTP/1.1" 200 33528 "http://www.stumbleupon.com/refer.php?url=http%3A%2F%2Fwww.towerinvestment.org%2F" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:31.0) Gecko/20100101 Firefox/31.0"
181.64.206.79 -  -  [18/Jul/2014:00:00:12 +0000] "GET / HTTP/1.1" 301 0 "-" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36"
192.99.200.213 -  -  [18/Jul/2014:00:00:13 +0000] "GET /?q=user%2Fregister HTTP/1.1" 200 31149 "-" "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT)"
191.50.10.28 -  -  [18/Jul/2014:00:00:14 +0000] "GET /_api/dynamicmodel HTTP/1.1" 200 3638 "http://www.estalagemdascasuarinas.com.br/" "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_6 like Mac OS X; pt-br) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/23.0.1271.100 Mobile/10B500 Safari/8536.25"
183.60.244.29 -  -  [18/Jul/2014:00:00:15 +0000] "GET /openconf.js HTTP/1.1" 404 2765 "http://www.baidu.com" "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:18.0) Gecko/20100101 Firefox/18.0"
186.101.77.31 -  -  [18/Jul/2014:00:00:16 +0000] "GET /_api/dynamicmodel HTTP/1.1" 200 4146 "http://www.iglesiaciudadderefugio.org/?gclid=CKiv2ITHzb8CFTJp7AodgRcAyQ" "Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0"
107.178.200.195 -  -  [18/Jul/2014:00:00:18 +0000] "GET / HTTP/1.1" 200 32131 "-" "AppEngine-Google; (+http://code.google.com/appengine; appid: s~wixarchive2)"
205.156.56.38 -  -  [18/Jul/2014:00:00:19 +0000] "GET /website/templates/html/all/26 HTTP/1.1" 200 119826 "http://www.wix.com/website/templates/html/all/25" "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0"
188.80.37.47 -  -  [18/Jul/2014:00:00:20 +0000] "GET /?ref=C2067488C HTTP/1.1" 200 30461 "http://afiliados-na-web.com/ad-rotator2.php" "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36"
173.226.206.82 -  -  [18/Jul/2014:00:00:21 +0000] "GET /_api/dynamicmodel HTTP/1.1" 200 573 "http://www.festivalatsea.com/" "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)"
65.96.132.199 -  -  [18/Jul/2014:00:00:22 +0000] "GET / HTTP/1.1" 200 34456 "http://www.google.com/url?sa=D&q=http://jewelltownevineyards.com/&usg=AFQjCNHJJK6-nL0_dDZJJIu7LOVb-K265w" "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36"
208.90.57.196 -  -  [18/Jul/2014:00:00:23 +0000] "GET / HTTP/1.1" 200 28504 "-" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1"
142.59.120.173 -  -  [18/Jul/2014:00:00:24 +0000] "GET /_api/dynamicmodel HTTP/1.1" 200 1994 "http://www.chaisland.com/" "Mozilla/5.0 (iPad; CPU OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) CriOS/36.0.1985.49 Mobile/11D257 Safari/9537.53"
177.195.188.37 -  -  [18/Jul/2014:00:00:26 +0000] "GET /_api/dynamicmodel HTTP/1.1" 200 3092 "http://www.elieteramos.com.br/" "Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D257 [FBAN/FBIOS;FBAV/12.1.0.24.20;FBBV/3214247;FBDV/iPhone5,1;FBMD/iPhone;FBSN/iPhone OS;FBSV/7.1.2;FBSS/2; FBCR/ClaroBrasil;FBID/phone;FBLC/pt_BR;FBOP/5]"
107.178.200.196 -  -  [18/Jul/2014:00:00:27 +0000] "GET / HTTP/1.1" 200 26732 "-" "AppEngine-Google; (+http://code.google.com/appengine; appid: s~wixarchive2)"
107.178.200.193 -  -  [18/Jul/2014:00:00:28 +0000] "GET /?_escaped_fragment_=todd-a.-bell-luncheon/zoom/cafq/image12nt&997ac4b8b2067f3f HTTP/1.1" 200 2237 "-" "Apple-iPhone AppEngine-Google; (+http://code.google.com/appengine; appid: s~acp-mobile)"
201.81.144.246 -  -  [18/Jul/2014:00:00:29 +0000] "GET / HTTP/1.1" 301 0 "-" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:29.0) Gecko/20100101 Firefox/29.0"
89.248.168.94 -  -  [18/Jul/2014:00:00:30 +0000] "POST /wordpresstest/xmlrpc.php HTTP/1.1" 200 33594 "-" "Mozilla/4.0 (compatible: MSIE 7.0; Windows NT 6.0)"
             """.split("\n").filter(_ != "")
}