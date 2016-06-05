package org.freefeeling.dbshell

/**
  * Created by zh on 16-6-4.
  */
sealed class DbShellException(message: String, cause: Exception) extends RuntimeException(message, cause)
object Exceptions {

  class DbShellInitialException(message: String, cause: Exception = null) extends DbShellException(message, cause)

}
