package dashboard

import unfiltered.request._
import dao.DashboardDao

/** unfiltered plan */
class App extends unfiltered.filter.Plan {


  def intent = {

    case req@GET(Path("/dashboard")) =>

      val dao = new DashboardDao()

      val report = dao.getDailyReport

      if(!report.isEmpty) {
        view(req, "dashboard.mustache", "report" -> Map("winnerSteps" -> report.getWinningSteps,
          "winnerGravatar" -> report.getWinningGravatar,
          "loserSteps" -> report.getLosingSteps ,
          "loserGravatar" -> report.getLosingGravatar))
      }
      else {
        view(req, "error.mustache")
      }
   }

  def view[T](req: HttpRequest[T], templateName: String, extra: (String, Any)*) = {
    val Params(params) = req
    Scalate(req, templateName, (params.toSeq ++ extra): _*)
  }
}

/** embedded server */
object Server {
  def main(args: Array[String]) {

    System.setProperty("org.eclipse.jetty.util.log.DEBUG","true")

    val http = unfiltered.jetty.Server.local(8000)
    http.context("/assets") { _.resources(
      new java.net.URL(getClass().getResource("/www/css"), ".")
    ) }.plan(new App).requestLogging("./dashboard/access.log").start()
  }
}
