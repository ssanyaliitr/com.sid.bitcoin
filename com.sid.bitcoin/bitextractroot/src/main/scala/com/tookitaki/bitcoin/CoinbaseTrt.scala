package com.tookitaki.bitcoin


/**
  * Created by SSanyal on 7/30/2018.
  */
  trait CoinbaseTrt {
  //added url_base in trait
  private val url_base = "https://www.coinbase.com/api/v2/prices/BTC-USD/historic?period="
  var time_prd: String

  val url = url_base + time_prd
}
