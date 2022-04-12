package com.app.ashikcarnottest.ui

import android.content.Context
import android.util.Log
import com.app.ashikcarnottest.R
import com.app.ashikcarnottest.apiservice.APIClient
import com.app.ashikcarnottest.apiservice.APIInterface
import com.app.ashikcarnottest.apiservice.RetrofitApiCallBack
import com.app.ashikcarnottest.utils.AppUtils
import com.app.ashikcarnottest.utils.MyLogger
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class HomeInteractor {

    fun callMarketMandiListApi(limit: Int, offset: Int, filters: String,
        apiListener: RetrofitApiCallBack<MarketApiResponse>) {

        apiListener.showProgress(true)
        val call: Call<JsonObject> =
            APIClient.getClient()!!.create(APIInterface::class.java)
                .marketmandilistapi("579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b", "json",offset.toString(),limit.toString(),filters)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                apiListener.showProgress(false)
                MyLogger.e("Api Response",response.toString())
                var marketMandiApiResponse : MarketApiResponse = Gson().fromJson(response.body().toString(), MarketApiResponse::class.java)
                if ("ok".equals(marketMandiApiResponse.status)){
                    apiListener.onApiSuccess(marketMandiApiResponse)
                }
                else{
                    apiListener.showProgress(false)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                call.cancel()
                apiListener.showProgress(false)
                apiListener.onApiError(t.message!!,400)
            }
        })
    }
}