package config

import java.text.SimpleDateFormat

object Config {

  //Source Data
  val googleDriveApiServiceAccount = "some-google-service-account@google.com"
  val googleDriveApiPrivateKeyFile = "/Users/michaelv/personal/playground/scala/fitbit-activity-summary/data/extractor/fitbit-activity-extractor-359e1268f0c0.p12"
  val ActivitiesCsvFileName = "mmv-daily-activity-summary"
  val ActivitiesCsvHeader = "code,date,steps,floors,calories,totalDistance"
  val ActivitiesFilePath = "/Users/michaelv/personal/playground/scala/fitbit-activity-summary/data/loaders/activities.csv"

  //DB
  val Neo4jRest = "http://localhost:7474/db/data"
  val CypherRest = Neo4jRest + "/cypher"
  val ActivitiesData = "file://" + ActivitiesFilePath

  //Dates
  val RawDataFormat = new SimpleDateFormat("dd/MM/yyyy")
  val PreferredDateFormat = new SimpleDateFormat("dd-MM-YYYY")

}
