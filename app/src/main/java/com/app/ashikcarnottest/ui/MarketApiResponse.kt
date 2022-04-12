package com.app.ashikcarnottest.ui

class MarketApiResponse {
    lateinit var status : String
    lateinit var title : String
    var total : Int = 0
    var count : Int = 0
    var limit : Int = 0
    var offset : Int = 0
    var records : ArrayList<MarketItem> = ArrayList()
    var field : ArrayList<FilterItem> = ArrayList()

}