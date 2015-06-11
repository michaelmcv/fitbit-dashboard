package extractor.service

import com.google.api.services.drive.Drive
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.http.HttpTransport
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import java.io.File
import java.util.Arrays
import config.Config
;

object DriveService {

  def drive: Drive = {

      val emailAddress: String = Config.googleDriveApiServiceAccount
      val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
      val httpTransport: HttpTransport  = GoogleNetHttpTransport.newTrustedTransport()
      val credential: GoogleCredential = new GoogleCredential.Builder()
        .setTransport(httpTransport)
        .setJsonFactory(jsonFactory)
        .setServiceAccountId(emailAddress)
        .setServiceAccountPrivateKeyFromP12File(new File(Config.googleDriveApiPrivateKeyFile))
        .setServiceAccountScopes(Arrays.asList("https://www.googleapis.com/auth/drive.readonly"))
        .build()

       new Drive.Builder(httpTransport, jsonFactory, credential).build()
    }

  def getCsvDownloadLink(availableExportLinks: Map[String, String]): String = {
    val csvFormatTuple = availableExportLinks.find({
        case (mediaType, urlLink) => mediaType == "text/csv"
      }
    )

    //return the link from the tuple
    csvFormatTuple.getOrElse(default = ("",""))._2
  }
}