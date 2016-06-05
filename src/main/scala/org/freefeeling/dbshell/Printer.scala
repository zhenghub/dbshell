package org.freefeeling.dbshell

/**
  * Created by zh on 16-6-5.
  */
trait Printer {

  def print(result: Result)

}

trait Printers {
  class BeautifyPrinter extends Printer {
    override def print(result: Result): Unit = {
      val b = new Beautifier(result.columnLabels)
      b.pprint(result.rows)
    }
  }

  class DontPrinter extends Printer {
    override def print(result: Result): Unit = {}
  }
}