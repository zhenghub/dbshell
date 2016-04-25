package org.freefeeling

import org.freefeeling.dbshell.{Result, Sqls, Executable}

/**
  * Created by zhenghu on 16-4-25.
  */
trait DbShell extends Executable with Sqls{
  implicit def result2Seq(result: Result) = result.rows
}

object DbShell extends DbShell{

}
