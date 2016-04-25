package org.freefeeling.dbshell

import scalikejdbc.WrappedResultSet

/**
  * Created by zhenghu on 16-4-25.
  */
trait Result{
  def merge(res: WrappedResultSet): Result

  def columnLabels: Seq[String]

  def rows: List[Seq[Any]]
}

object Result {

  class ResultWithNoMeta extends Result{
    override def merge(res: WrappedResultSet): Result = {
      new ResultWithMeta((1 to res.metaData.getColumnCount).map(res.metaData.getColumnLabel), List((1 to res.metaData.getColumnCount).map(res.any)))
    }

    override def columnLabels: Seq[String] = ???

    override def rows: List[Seq[Any]] = ???
  }

  case class ResultWithMeta(val columnLabels: Seq[String], val rows: List[Seq[Any]]) extends Result{
    override def merge(res: WrappedResultSet): Result = {
      ResultWithMeta(columnLabels, rows.:+ ((1 to columnLabels.length).map(res.any)))
    }
  }

  case class SummaryResult(msg: String) extends Result {
    override def merge(res: WrappedResultSet): Result = ???

    override def columnLabels: Seq[String] = ???

    override def rows: List[Seq[Any]] = ???
  }

  def apply():Result = new ResultWithNoMeta

  def apply(msg: String) = SummaryResult(msg)

}
