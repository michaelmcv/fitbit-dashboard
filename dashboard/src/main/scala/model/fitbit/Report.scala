package model.fitbit

import util.MD5Util

class Report(val userActivity: UserActivity, val rivalActivity: UserActivity) {

  //def apply(userActivity: UserActivity,rivalActivity: UserActivity) = {
  //
  //}

  def getWinningSteps: Int = {
    if(userActivity.steps > rivalActivity.steps )
      return userActivity.steps
    else
      return rivalActivity.steps
  }

  def getWinningGravatar: String = {
    if(userActivity.steps > rivalActivity.steps )
      return MD5Util.md5Hex(userActivity.email)
    else
      return MD5Util.md5Hex(rivalActivity.email)
  }

  def getLosingSteps: Int = {
    if(userActivity.steps < rivalActivity.steps )
      return userActivity.steps
    else
      return rivalActivity.steps
  }

  def getLosingGravatar: String = {
    if(userActivity.steps < rivalActivity.steps )
      return MD5Util.md5Hex(userActivity.email)
    else
      return MD5Util.md5Hex(rivalActivity.email)
  }

  def isEmpty: Boolean = {
    if(userActivity.steps == 0 && rivalActivity.steps ==0 && userActivity.email == "" && rivalActivity.email == "")
      return true
    else
      return false
  }
}
