package extractor.model

import java.util.Date
import java.text.SimpleDateFormat
import config.Config

case class ActivityMetaData(val code: String, val date: Date, val steps: Int, val floors: Int, val calories: Int, val totalDistance: Double) {

  override def toString(): String = {

    ("%s,%s,%d,%d,%d,%1.2f").format(code, Config.PreferredDateFormat.format(date), steps, floors, calories, totalDistance)
  }
}
