package org.freefeeling.dbshell

import org.freefeeling.dbshell.Result.{QueryResult, EmptyResult}

/**
  * Created by zh on 16-6-5.
  */
trait Printer {

  def print(result: EmptyResult): Unit

  def print(result: QueryResult): Unit

  def print(resCnt: Int): Unit

}

trait Printers {
  class BeautifyPrinter extends Printer {
    def print(result: QueryResult): Unit = {
      val b = new Beautifier(result.columnLabels)
      b.pprint(result.rows)
    }

    override def print(result: EmptyResult): Unit = ???

    override def print(resCnt: Int): Unit = ???
  }

  class DontPrinter extends Printer {
    override def print(result: QueryResult): Unit = {}

    override def print(result: EmptyResult): Unit = ???

    override def print(resCnt: Int): Unit = ???
  }
}