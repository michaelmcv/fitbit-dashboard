package loader.service

import com.stackmob.newman._
import com.stackmob.newman.dsl._

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import java.net.URL
import net.liftweb.json.scalaz.JsonScalaz._
import config.Config

object DataService {

  def loadUsers: String = {

    val query =  "MERGE (mbm:User {code: 'mbm', firstName: 'Mary', lastName: 'Mullan', email:'b.mullan@kainos.com'}) " +
                 "MERGE (mmv:User {code: 'mmv', firstName: 'Michael', lastName: 'McVeigh', email:'michaelmc_veigh@hotmail.com'})"

    executeCypher(query, "")
  }

  def makeRivals: String = {

    val query =  "MATCH (mmv: User {code: 'mmv'}), (mbm: User {code: 'mbm'}) " +
                 "MERGE (mbm)-[:RIVAL]->(mmv)"

    executeCypher(query, "")
  }

  def loadActivities: String = {

    val query =  "LOAD CSV WITH HEADERS FROM '" + Config.ActivitiesData + "' AS csvLine " +
                 "MATCH (thisUser: User {code: csvLine.code}) " +
                 "MERGE (activity: Activity {date: csvLine.date, steps: csvLine.steps, floors: csvLine.floors, calories:csvLine.calories, totalDistance:csvLine.totalDistance}) " +
                 "MERGE (thisUser)-[:COMPLETED]->(activity)"

    executeCypher(query, "")
  }

  def executeCypher(cypherQuery: String, cypherParams: String):String = {

    val body = "{\"query\" : \"%s\", \"params\": {%s}}".format(cypherQuery, cypherParams)
    println("json: " + toJSON(body))

    //force thread execution to wait 2 seconds (more than enough time for simple cyphers) for the promise to return
    implicit val httpClient = new ApacheHttpClient
    val cypherUrl = new URL(Config.CypherRest)
    val response = Await.result(POST(cypherUrl).setBody(body).apply, 2.second)

    response.bodyString
  }

}
