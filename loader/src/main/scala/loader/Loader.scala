package loader

import loader.service.DataService

object Loader {

  def main(args: Array[String]):Unit = {
    println("Loading starting ...")

    println(DataService.loadUsers)
    println(DataService.makeRivals)
    DataService.loadActivities

    println("Loading complete.")
  }

}
