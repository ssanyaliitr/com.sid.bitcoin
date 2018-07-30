package com.tookitaki.bitcoin

import org.joda.time.format.DateTimeFormat

/**
  * Created by SSanyal on 7/30/2018.
  */
object BitCoinDriver extends App {

  var time_prd = args(0) // "week" | "day" | "year" | "all" | "month" | "yyyy-mm-dd"
  val time1 = args(1) //args(1) // "2018-07-01" <yyyy-mm-dd>
  val time2 = args(2) //args(2) // "2018-07-30" <yyyy-mm-dd>
  val days = args(3) // "4"

  time_prd match {
    case "week" | "day" | "year" | "all" | "month" =>
      {
        val bitext = new BitcoinExtract(time_prd)
        bitext.extractBitcoinPrice()
        //bitext.extractBitcoinPrice().take(10).foreach(println)
      }
    case _ => {
      val fmt = DateTimeFormat forPattern "yyyy-mm-dd"
      try {
        time_prd = fmt.parseDateTime(time_prd).toString("yyyy-mm-dd")
        val bitext = new BitcoinExtract(time_prd)
        bitext.extractBitcoinPrice(time_prd)
        //bitext.extractBitcoinPrice(time_prd).foreach(println)
      }
      catch {
        case e: Exception => throw new Exception(s"Invalid date format: Found ${time_prd}.Format should be in yyyy-mm-dd")
      }
    }
  }

  //if time1 and time2 and days all three present then give moving average
  if(time1 != "" && time2 != "" && days != ""){
    println("Hi")
    try{
      if(days.toInt < 0){
        throw new Exception("days should be a positive integer")
      }
    }
    catch {
      case e: Exception => throw new Exception("days should be a integer")
    }
    val ma = new MovingAverage(time1,time2,days.toInt)

    ma.extract_ma()
    println(ma.extract_ma().take(10).foreach(println))
  }

  //if value of days present then give recommendation
  if(days != ""){
    //println("Hi")
    try{
      if(days.toInt < 0){
        throw new Exception("days should be a positive integer")
      }
    }
    catch {
      case e: Exception => throw new Exception("days should be a integer")
    }
    val recommendation = new Buy_Sell_Hold_Recommend(days.toInt)

    //prints the recommendation
    println(recommendation.give_recommendation())
  }
}
