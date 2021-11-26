package com.yusril.skripsi_app.ui.forecastFuture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.entity.Status
import com.yusril.skripsi_app.response.DataFutureByTypeMethodItem
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class ForecastFutureModel: ViewModel() {
    companion object {
        private const val URL_FORECAST_FUTURE = BuildConfig.URL_FORECAST_FUTURE
    }
    var statusForecastFuture = MutableLiveData<ArrayList<Status>>()
    var ForecastFutureByDataType = MutableLiveData<ArrayList<DataFutureByTypeMethodItem>>()
    fun setPostForecastFuture(period:Int){
        val client = AsyncHttpClient()
        val listItems = ArrayList<Status>()
        val url= URL_FORECAST_FUTURE
        val params = RequestParams()
        params.put("period", period)
        client.post(url,params,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val status_data:Boolean= jsonObject["status"] as Boolean
                    val status=Status(
                        status_data
                    )
                    listItems.add(status)
                    statusForecastFuture.postValue(listItems)
                    Log.d("setForecastFuture", status.toString())
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"

                }
                Log.d("onFailure setForecastFuture", errorMessage)
            }
        })
    }
    fun setStatusForecastFutureByTypeMethod(
        id_tourist_data_type:Int,
        id_method_type:Int
    ){
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.put("id_tourist_data_type", id_tourist_data_type)
        params.put("id_method_type", id_method_type)
        val url= URL_FORECAST_FUTURE
        val listItems = ArrayList<Status>()
        client.get(url,object:
            AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)

                    Log.d("setStatusForecastFuture", result.toString())
                    Log.d("setStatusForecastFuture", jsonObject.toString())
                    val status_data:Boolean= jsonObject["status"] as Boolean
                    val status=Status(
                        status_data
                    )
                    listItems.add(status)
                    statusForecastFuture.postValue(listItems)
                    Log.d("setStatusForecastFuture", status.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("setStatusForecastFutureFail", error.toString())
            }
        }
        )
    }
    fun setForecastFutureByTypeMethod(
        id_tourist_data_type:Int,
        id_method_type:Int
    ){
        val listData = ArrayList<DataFutureByTypeMethodItem>()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.put("id_tourist_data_type", id_tourist_data_type)
        params.put("id_method_type", id_method_type)
        val url= URL_FORECAST_FUTURE
//        val url="http://192.168.100.6/skripsi-rest-forecasting/api/Future?id_tourist_data_type=1&id_method_type=1"
        client.get(url,params,object:
            AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val dataArray = jsonObject.getJSONArray("data_future")
                    Log.d("setForecastFutureBy1", result.toString())
                    Log.d("setForecastFutureBy2", jsonObject.toString())
                    Log.d("setForecastFutureBy3", dataArray.toString())
                    for (i in 0 until dataArray.length()) {
                        val dataItem = dataArray.getJSONObject(i)
                        Log.d("dataItem", i.toString())
                        val no=i+1
                        val idForecastFuture=dataItem.getString("id_forecast_future")
                        val month=dataItem.getString("season_future")
                        val year_future=dataItem.getString("year_future")
                        val t_future=dataItem.getString("t_future")
                        val id_seasonal_index=dataItem.getString("id_seasonal_index")
                        val unadjusted_forecast=dataItem.getString("unadjusted_forecast")
                        val adjusted_forecast=dataItem.getString("adjusted_forecast")
                        val id_tourist_data_type=dataItem.getString("id_tourist_data_type")
                        val id_method_type=dataItem.getString("id_method_type")
                        val dataTouristItem= DataFutureByTypeMethodItem(
                            no.toString(),
                            t_future,
                            month,
                            id_method_type,
                            adjusted_forecast,
                            id_tourist_data_type,
                            id_seasonal_index,
                            unadjusted_forecast,
                            idForecastFuture,
                            year_future
                        )
                        listData.add(dataTouristItem)
                        Log.d("setForecastFutureBy4", idForecastFuture.toString())
                    }
                    ForecastFutureByDataType.postValue(listData)
                    Log.d("Dataaaa", listData.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error.toString())
            }
        }
        )
    }

    fun getStatusForecastFuture(): LiveData<ArrayList<Status>> {
        return statusForecastFuture
    }
    fun getForecastFutureByTypeMethod(): LiveData<ArrayList<DataFutureByTypeMethodItem>> {
        return ForecastFutureByDataType
    }
}