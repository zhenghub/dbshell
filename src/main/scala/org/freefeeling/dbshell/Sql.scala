package org.freefeeling.dbshell

import scalikejdbc._

/**
  * Created by zhenghu on 16-4-25.
  */
trait Executable {

  implicit def str2Sql(str: String) = new Sql(str)

}

class Sql(str: String){

  val underlying = SQL(str)

  def !(implicit session: DBSession) = {
    try {
      underlying.foldLeft(Result())(_.merge(_))
    }catch {
      case e: java.sql.SQLException =>
        if(e.getMessage == "ResultSet is from UPDATE. No Data.") {
          Result(s"RESULT: ${underlying.execute().apply()}")
        } else {
          throw e
        }
    }
  }

}