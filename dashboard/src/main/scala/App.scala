package dashboard

import unfiltered.request._
import dao.DashboardDao
import com.github.nscala_time.time.Imports._

/** unfiltered plan */
class App extends unfiltered.filter.Plan {

  def intent = {

    case req@GET(Path("/dashboard")) =>

      //check this as using concatenation in the cypher query!
      val queryParam = req.parameterValues("forDate").headOption.getOrElse("")
      val requestedDate = parseDate(queryParam).getOrElse(DateTime.yesterday).toString("dd-MM-YYYY")

      val dao = new DashboardDao()
      //hmmm - must be a more obvious way
      val report =  requestedDate match {
        case "" =>  dao.getReport()
        case nonEmptyDate => dao.getReport(nonEmptyDate)
      }

      //get 29 days back from yesterday, as map of maps (a syntax supporting looping in scalate template)
      val datesVector = for (i <- 1 to 30)
                        yield Map("date" -> (DateTime.yesterday - i.days).toString("dd-MM-YYYY"))
      val dates = Map("dates" -> datesVector.toList)

      if(!report.isEmpty) {
        view(req, "dashboard.mustache", "report" -> Map("winnerSteps" -> report.getWinningSteps,
          "winnerGravatar" -> report.getWinningGravatar,
          "loserSteps" -> report.getLosingSteps ,
          "loserGravatar" -> report.getLosingGravatar), "dateList" -> dates)
      }
      else {
        view(req, "error.mustache", "dateList" -> dates)
      }
   }

  def view[T](req: HttpRequest[T], templateName: String, extra: (String, Any)*) = {
    val Params(params) = req
    Scalate(req, templateName, (params.toSeq ++ extra): _*)
  }

  def parseDate(input: String) = try {
    val fmt = DateTimeFormat forPattern "dd-MM-yyyy"
      Some(fmt parseDateTime input)
  } catch {
    case e: IllegalArgumentException => None
  }
}

/** embedded server */
object Server {
  def main(args: Array[String]) {

    System.setProperty("org.eclipse.jetty.util.log.DEBUG","true")

    //bind to all interfaces on host
    val http = unfiltered.jetty.Server.http(8000,"0.0.0.0")
    http.context("/assets") { _.resources(
      new java.net.URL(getClass().getResource("/www/css"), ".")
    ) }.plan(new App).requestLogging("./dashboard/access.log").start()
  }
}
