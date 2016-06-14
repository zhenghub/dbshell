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
  implicit def str2Sql(sql: String) = new Sql(sql)

  implicit def sclikeSql2Sql(sql: SQL[_, _]) = new Sql(sql)
}

object Sql {
  val queryPattern = """^\s*((?i)select|show|desc|describe|explain).*""".r
}

class Sql(sql: SQL[_, _]) {

  def this(sqlString: String) {
    this(SQL(sqlString))
  }

  private def statement = sql.statement

  private def isQuery = {
    Sql.queryPattern.findFirstMatchIn(statement).isDefined
  }

  /**
    * execute sql
    * @param session
    * @return
    */
  def ??(implicit session: DBSession): Result = {
    if (isQuery) {
      query
    } else {
      Result(s"${execute} rows affected")
    }
  }

  /**
    * query
    * @param session
    * @return
    */
  def query(implicit session: DBSession): Result = {
    sql.foldLeft(Result())(_.merge(_))
  }

  def ?#(implicit session: DBSession) = query

  def execute(implicit session: DBSession) = sql.executeUpdate().apply()

  def ?!(implicit session: DBSession) = execute

  /**
    * execute sql and print result
    * @param session
    * @param outLook
    * @return
    */
  def ?(implicit session: DBSession, outLook: ShellOutLook): Result = {
    val res = outLook.execute(??)
    outLook.display(res)
    res
  }

}