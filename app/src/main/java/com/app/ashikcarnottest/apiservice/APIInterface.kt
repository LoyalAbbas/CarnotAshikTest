package com.app.ashikcarnottest.apiservice

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("/resource/9ef84268-d588-465a-a308-a864a43d0070")
    fun marketmandilistapi(
        @Query("api-key") api_key: String,
        @Query("format") format: String,
        @Query("offset") offset: String,
        @Query("limit") limit: String,
        @Query("filters") filters: String
    ): Call<JsonObject>
}