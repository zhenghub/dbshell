package org.freefeeling

import org.freefeeling.dbshell.{DBThings, Result, Sqls, Executable}

/**
  * Created by zhenghu on 16-4-25.
  */
trait DbShell extends Executable with Sqls with DBThings{
  implicit def result2Seq(result: Result) = result.rows

}

object DbShell extends DbShell{
}