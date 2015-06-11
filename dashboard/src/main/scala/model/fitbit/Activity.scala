package model.fitbit

abstract class Activity {
  def steps: Int
}

case class UserActivity(email: String, steps: Int) extends Activity {

}
