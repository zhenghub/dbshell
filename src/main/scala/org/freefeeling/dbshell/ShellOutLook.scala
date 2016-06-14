package org.freefeeling.dbshell

import org.freefeeling.dbshell.Result.{ErrorResult, EmptyResult, QueryResult, SummaryResult}

import scala.util.{Failure, Success, Try}

/**
  * Created by zhenghu on 16-6-14.
  */
trait ShellOutLook {

  def execute(exec: => Result): Result

  def display(result: Result)

  def lastException

}

class DefaultShellOutLook(printer: Printer) extends ShellOutLook {
  private var _lastException: Option[Throwable] = None

  override def execute(exec: => Result): Result = {
    try {
      exec
    } catch {
      case e: Throwable =>
        this._lastException = Some(e)
        ErrorResult(e)
    }
  }

  override def display(result: Result): Unit = {
    result match {
      case r: EmptyResult =>
        println("Empty set")
      case r: QueryResult =>
        printer.print(r)
      case SummaryResult(msg) =>
        println(msg)
      case e: ErrorResult =>
        System.err.println(e)
    }
  }

  override def lastException: Unit = {
    _lastException.foreach(_.printStackTrace(System.err))
  }
}
