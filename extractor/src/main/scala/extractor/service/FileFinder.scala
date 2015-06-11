package extractor.service

import com.google.api.services.drive.model.File

class FitBitFilesFinder(val files: Set[File]) {

  def find(title: String): Option[File] = {

    for(file <- files) {
      println("Trying to find file with title: " + title + " comparing with " + file.getTitle)
      if(file.getTitle == title) {
        return Some(file)
      }
    }

    None
  }
}
