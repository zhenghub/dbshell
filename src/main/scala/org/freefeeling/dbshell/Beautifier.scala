package org.freefeeling.dbshell;

import java.io.PrintWriter

/**
  * Created by zh on 16-6-5.
  */

class Beautifier(headers: Seq[String]) {
  import Beautifier._
  private val sizes = headers.map(sizeOf).toArray

  def beautifyRows(rows: Seq[Seq[Any]]) = {
    val newRows = rows.map{row =>
      val strRow = row.map(trans2String)
      strRow.zipWithIndex.foreach{ kv =>
        val (s, idx) = kv
        if(sizes(idx) < s.length)
          sizes(idx) = s.length
      }
      strRow
    }

    newRows.map(_.zipWithIndex.map(vi => fixLength(vi._1, sizes(vi._2))))
  }

  def beautifiedHeaders = headers.zipWithIndex.map(vi => fixLength(vi._1, sizes(vi._2)))

  private def fixLength(str: String, length: Int) = {
    if(str.length == length)
      " " + str + " "
    else
      " " + str + " " * (length - str.length) + " "
  }

  def pprint(rows: Seq[Seq[Any]]) = {
    val nRows = beautifyRows(rows)
    val splitLine = ("+" + sizes.map(l => "-" * (l + 2)).mkString("+") + "+")
    println(splitLine)
    println("|" + beautifiedHeaders.mkString("|") + "|")
    println(splitLine)
    nRows.foreach(r => println("|" + r.mkString("|") + "|"))
    println(splitLine)
  }
}

object Beautifier {

  def trans2String(item: Any) = {
    item match {
      case null => "NULL"
      case s: String =>
        s
      case _ =>
        item.toString
    }
  }

  def sizeOf(item: String): Int = item.length

  def sizeOf(item: Int): Int = {
    item match {
      case i if i >= 100000 =>
        10
      case i if i >= 0 =>
        5
      case i if i <= -100000 =>
        11
      case _ =>
        6
    }
  }

  def sizeOf(item: Long): Int = {
    item match {
      case i if i >= 1000000000000000L =>
        19
      case i if i >= 10000000000L =>
        15
      case i if i >= 100000L =>
        10
      case i if i >= 0L =>
        5
      case i if i <= -1000000000000000L =>
        20
      case i if i <= -10000000000L =>
        16
      case i if i <= -100000L =>
        11
      case _ =>
        5
    }
  }

  def sizeOf(item: Double): Int = item.toString.length
}
