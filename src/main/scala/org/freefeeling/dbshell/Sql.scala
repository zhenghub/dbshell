package org.freefeeling.dbshell

import org.freefeeling.dbshell.Result.{SummaryResult, QueryResult, EmptyResult}
import scalikejdbc._
import scalaz.syntax.all._
import scalaz._

import scalaz.Functor

/**
  * Created by zhenghu on 16-4-25.
  */
trait Executable {

  implicit def str2Sql(str: String) = new Sql(str)

}

object Sql {
  val queryPattern = """^\s*((?i)select|show|desc|describe).*""".r

  def pprint(result: Result)(implicit printer: Printer) = {
    result match {
      case r: EmptyResult =>
        println("Empty set")
      case r: QueryResult =>
        printer.print(r)
      case SummaryResult(msg) =>
        println(msg)
    }
  }
}

class Sql(str: String) {

  val underlying = SQL(str)

  private def isQuery = {
    Sql.queryPattern.findFirstMatchIn(str).isDefined
  }

  def ??(implicit session: DBSession) = {
    if (isQuery) {
      underlying.foldLeft(Result())(_.merge(_))
    } else {
      Result(s"${underlying.executeUpdate().apply()} rows affected")
    }
  }

  def ?(implicit session: DBSession, printer: Printer) = {
    val res = ??
    Sql.pprint(res)
    res
  }

}