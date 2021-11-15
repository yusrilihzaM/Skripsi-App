package com.yusril.skripsi_app.api

import com.yusril.skripsi_app.response.DataTouristItem
import com.yusril.skripsi_app.response.GetTouristDataTypeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/TouristDataType")
    fun getTouristDataType(): Call<DataTouristItem>
}