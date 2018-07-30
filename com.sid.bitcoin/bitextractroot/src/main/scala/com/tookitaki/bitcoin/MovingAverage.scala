package com.tookitaki.bitcoin

import org.joda.time.Days
import org.joda.time.format.DateTimeFormat


/**
  * Created by SSanyal on 7/31/2018.
  */
class MovingAverage(val time1: String,val time2: String,val days: Int) {

    check_dt_validity(time1)
    check_dt_validity(time2)
    if(!check_dt_diff()) {
      throw new Exception(s"Days given ${time1} and ${time2} is less than period ${days}")
      System.exit(101)
    }
    val bitext_for_ma = new BitcoinExtract("all")
    val bitcoin_price = bitext_for_ma.extractBitcoinPrice().filter(a => (a.time.split('T')(0) >= time1 && a.time.split('T')(0) <= time2))
    //bitcoin_price.take(10).foreach(println)

    def extract_ma(): Seq[time_price_ma] = {
      var i = 0
      val ma_seq = bitcoin_price.map(a => a.price).sliding(days).map(a => if(a.size == days) a.sum/days else 0).toSeq
     //ma_seq.take(10).foreach(println)
      bitcoin_price.zip(ma_seq).map(a => time_price_ma(a._1.time,a._1.price,a._2))
  }

  private def check_dt_validity(time: String) = {
    val fmt = DateTimeFormat forPattern "yyyy-mm-dd"
    try {
     fmt.parseDateTime(time).toString("yyyy-mm-dd")
    }
    catch {
      case e: Exception => throw new Exception(s"Invalid date format: Found ${time}.Format should be in yyyy-mm-dd")
    }
  }

  private def check_dt_diff(): Boolean = {
    val fmt = DateTimeFormat forPattern "yyyy-mm-dd"
    //println(Days.daysBetween(fmt.parseDateTime(time2),fmt.parseDateTime(time1)).getDays.abs)
    Days.daysBetween(fmt.parseDateTime(time1),fmt.parseDateTime(time2)).getDays.abs >= days
  }

}

case class time_price_ma(time: String,price: Float,ma: Float)
