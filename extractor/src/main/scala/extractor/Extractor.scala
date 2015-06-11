package extractor

import extractor.service.{FitBitFilesFinder, DriveService}

import scala.collection.JavaConverters._
import scala.io.Source
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets

import com.google.api.services.drive.model.{File, FileList}
import com.google.api.services.drive.Drive
import com.google.api.client.http.{HttpResponse, GenericUrl}
import extractor.model.ActivityMetaData
import config.Config

object Extractor {

  def main(args: Array[String])  {

    println("Extraction starting ...")

    val driveService: Drive = DriveService drive

    val fileList: FileList  = driveService.files.list.execute
    val files = fileList.getItems.asScala.toSet

    val fitBitFile = Set(Config.ActivitiesCsvFileName).flatMap((new FitBitFilesFinder(files)).find(_)).head

    val exportLinks = fitBitFile.getExportLinks.asScala.toMap

    val fitbitCsvDownloadUrl = DriveService.getCsvDownloadLink(exportLinks)

    val download: HttpResponse = driveService.getRequestFactory().buildGetRequest(new GenericUrl(fitbitCsvDownloadUrl)).execute()

    val activitiesData = Source.fromInputStream(download.getContent).getLines()
    val allActivities = activitiesData.map(dataLine =>  transformData(dataLine.split(",")))

    writeDataLoader(allActivities.toList)

    println("Extraction complete.")
  }

  def transformData(data: Array[String]): ActivityMetaData = {

    new ActivityMetaData(data(0), Config.RawDataFormat.parse(data(1)), data(2).toInt, data(3).toInt, data(4).toInt, data(7).toDouble)
  }

  def writeDataLoader(activities: List[ActivityMetaData]): Unit = {

    Files.write(Paths.get(Config.ActivitiesFilePath),
      (Config.ActivitiesCsvHeader +"\n"+ activities.sortBy(_.date).map(_+"\n").mkString).getBytes(StandardCharsets.UTF_8))
  }
}
