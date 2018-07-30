package com.tookitaki.bitcoin

import org.joda.time.{DateTime}

/**
  * Created by SSanyal on 7/31/2018.
  */
class Buy_Sell_Hold_Recommend(val days: Int) {
  val bitext_for_ma = new BitcoinExtract("all")
  val bitcoin_price_lt = bitext_for_ma.extractBitcoinPrice().filter(a => (a.time.split('T')(0) >= DateTime.now().plusDays(-days).toString("yyyy-mm-dd") && a.time.split('T')(0) <= DateTime.now().toString("yyyy-mm-dd"))).map(a => a.price)
  val bitcoin_price_st = bitext_for_ma.extractBitcoinPrice().filter(a => (a.time.split('T')(0) >= DateTime.now().plusDays(-days/2).toString("yyyy-mm-dd") && a.time.split('T')(0) <= DateTime.now().toString("yyyy-mm-dd"))).map(a => a.price)

  //when short term ma crosses long term ma then BUY
  //if short term ma goes below long tern ma then sell
  //if equal then hold

  def give_recommendation(): String = {
    if(bitcoin_price_st.sum > bitcoin_price_lt.sum){
      "BUY"
    }
    else if (bitcoin_price_st.sum < bitcoin_price_lt.sum){
      "SELL"
    }
    else "HOLD"
  }
}
