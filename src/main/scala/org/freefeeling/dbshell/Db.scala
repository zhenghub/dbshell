package org.freefeeling.dbshell

import scalikejdbc.{SQL, WrappedResultSet, ConnectionPool, DBSession}

/**
  * Created by zhenghu on 16-4-26.
  */

case class Db(poolName: String, dbUri: String, userName: String, password: String) {
  lazy val pool = {
    ConnectionPool.add(poolName, dbUri, userName, password)
    ConnectionPool(poolName)
  }

  def execute[T](func: DBSession => T) = {
    val session = DBSession(pool.borrow())
    try {
      func(session)
    } finally {
      session.close()
    }
  }

  def borrow = DBSession(pool.borrow())
}

class Table(val tableName: String, val columns: Seq[String]) {

  val col2Index = scala.collection.mutable.HashMap[String, Int]()

  def colIndex(colName: String): Option[Int] = col2Index.get(colName).orElse {
    columns.indexOf(colName) match {
      case -1 => None
      case i =>
        col2Index(colName) = i
        Option(i)
    }
  }
}

trait DBThings {
  def table(tableName: String)(implicit session: DBSession) = {
    val columns =
      SQL(s"desc ${tableName}").map(rs => rs.string(1).toLowerCase()).list().apply()
    new Table(tableName, columns)
  }

  def db(poolName: String, dbUri: String, userName: String, password: String) = Db(poolName, dbUri, userName, password)
}

object DBThings extends DBThings
