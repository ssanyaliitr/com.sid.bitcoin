package com.tookitaki.bitcoin

import play.api.libs.json.Json

import scala.io.Source.fromURL

/**
  * Created by SSanyal on 7/30/2018.
  */

//uses CoinbaseTrt for url,base_url and time_prd
class BitcoinExtract(var time_prd: String) extends CoinbaseTrt  {

  val bitExt = fromURL(url).mkString

  //Functions of the same name One extracts based on day week month year all etc and other extracts a custom date from all
  def extractBitcoinPrice(): Seq[Time_Price] ={

    //Json extract using play.api.libs.json.Json
    val jsonExt = Json.parse(bitExt) \ "data" \ "prices"

    (jsonExt.\\("price").zip(jsonExt.\\("time")))
      .map(a => Time_Price(a._2.toString().replace("\"",""),a._1.toString().replace("\"","").toFloat))
  }

  def extractBitcoinPrice(time_val: String): Seq[Time_Price] ={
    time_prd = "all"
    extractBitcoinPrice().filter(a => a.time.split('T')(0) == s"${time_val.trim}")
  }
}

case class Time_Price(time : String,price: Float)


