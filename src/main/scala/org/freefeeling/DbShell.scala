package org.freefeeling

import org.freefeeling.dbshell.Exceptions.DbShellInitialException
import org.freefeeling.dbshell._

/**
  * Created by zhenghu on 16-4-25.
  */
trait DbShell extends Executable with Sqls with DBThings with Printers{
  implicit def result2Seq(result: Result) = result.rows
}

object DbShell extends DbShell {

  def initDb(argStr: String): Db = {
    initDb(argStr.split("\\s+"))
  }

  def initDb(args: Array[String]): Db = {

    val params = args.map(kv => kv.splitAt(kv.indexOf('='))).map(kv => kv._1 -> kv._2.drop(1)).toMap

    def require(fieldShort: String, field: String) = params.get(fieldShort).getOrElse(throw new DbShellInitialException(s"${field} is required to init db shell"))
    val jdbc = require("-j", "jdbc")
    val user = require("-u", "user")
    val password = require("-p", "password")

    jdbc.split(":")(1).toLowerCase() match {
      case "mysql" =>
        Class.forName("com.mysql.jdbc.Driver")
      case "postgresql" =>
        Class.forName("org.postgresql.Driver")
      case "h2" =>
        Class.forName("org.h2.Driver")
      case "hsqldb" =>
        Class.forName("org.hsqldb.jdbc.JDBCDriver")
      case "derby" =>
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver")
      case "sqlite" =>
        Class.forName("org.sqlite.JDBC")
      case db =>
        throw new DbShellInitialException(s"unkown database ${db}, it needs a driver")
    }
    db("console", jdbc, user, password)
  }

  implicit val tablePprinter = new BeautifyPrinter
}