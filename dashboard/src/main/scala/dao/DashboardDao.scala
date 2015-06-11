package dao

import org.anormcypher.{CypherRow, Cypher, Neo4jREST}
import com.github.nscala_time.time.Imports._
import model.fitbit.{UserActivity, Report}

class DashboardDao {

  implicit val connection = Neo4jREST("localhost", 7474, "/db/data/")

  def getDailyReport: Report = {

    val yesterday = DateTime.yesterday.toString("dd-MM-YYYY")
    val query = "MATCH (mbm)-[:RIVAL]-(mmv) " +
                s"MATCH (mbm)-[:COMPLETED]-(mbmLatestActivity:Activity {date:'${yesterday}'}),(mmv)-[:COMPLETED]-(mmvLatestActivity:Activity {date:'${yesterday}'}) " +
                "WHERE mbm.code = 'mbm' " +
                "RETURN mbm.email as mbmEmail, mbmLatestActivity.steps as mbmSteps, mmv.email as mmvEmail, mmvLatestActivity.steps as mmvSteps"


    val results = Cypher(query)().collect {
      case CypherRow(mbmEmail: String, mbmSteps: String, mmvEmail: String, mmvSteps: String) => {
          new Report(new UserActivity(mbmEmail, mbmSteps.toInt), new UserActivity(mmvEmail, mmvSteps.toInt))
        }
      }

    results.headOption.getOrElse(new Report(new UserActivity("", 0), new UserActivity("", 0)))
  }
}


