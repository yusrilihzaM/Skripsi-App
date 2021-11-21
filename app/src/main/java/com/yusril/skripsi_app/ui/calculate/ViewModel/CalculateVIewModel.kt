package com.yusril.skripsi_app.ui.calculate.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.entity.CountData
import com.yusril.skripsi_app.entity.Status
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class CalculateVIewModel: ViewModel() {
    companion object {
        private const val URL_CALCULATE = BuildConfig.URL_CALCULATE
        private const val URL_COUNT_FORECAST = BuildConfig.URL_COUNT_FORECAST
    }
    var statusCalculate = MutableLiveData<ArrayList<Status>>()
    var statusCountData = MutableLiveData<ArrayList<CountData>>()
    fun setCalculate(){
        val client = AsyncHttpClient()
        val listItems = ArrayList<Status>()
        val url= URL_CALCULATE
        client.get(url,object : AsyncHttpResponseHandler(){
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
                    statusCalculate.postValue(listItems)
                    Log.d("setCalculate", status.toString())
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
                Log.d("onFailure", errorMessage)
            }
        })
    }

    fun setCountData(){
        val client = AsyncHttpClient()

        val listItems = ArrayList<CountData>()

        val url= URL_COUNT_FORECAST
        client.get(url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val status_data:Boolean= jsonObject["status"] as Boolean
                    val countData= jsonObject["message"].toString()
                    val status=CountData(
                        status_data,
                        countData
                    )
                    listItems.add(status)
                    statusCountData.postValue(listItems)
                    Log.d("setCountData", result.toString())
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
                Log.d("onFailure", errorMessage)
            }
        })
    }
    fun getStatusCountData(): LiveData<ArrayList<CountData>> {
        return statusCountData
    }
    fun getStatusCalculate(): LiveData<ArrayList<Status>> {
        return statusCalculate
    }
}