package com.yusril.skripsi_app.ui.datatourist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.response.DataTouristItem
import com.yusril.skripsi_app.response.DataTouristTypeItem

import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DataTouristViewModel: ViewModel() {
    companion object {
        private const val URL_DATA_TOURIST_TYPE_PLACE = BuildConfig.URL_DATA_TOURIST_TYPE_PLACE
        private const val URL_POST_DATA_TOURIST_TYPE = BuildConfig.URL_DATA_TOURIST_TYPE
        private const val URL_POST_DATA_TOURIST= BuildConfig.URL_DATA_TOURIST
        private const val URL_REST_BASE= BuildConfig.URL_REST_BASE
    }
    val listTouristDataType = MutableLiveData<ArrayList<DataTouristTypeItem>>()
    val listTouristData = MutableLiveData<ArrayList<DataTouristItem>>()
    fun setTouristDataType(){
        val listItems = ArrayList<DataTouristTypeItem>()
        val client = AsyncHttpClient()

        val url= URL_POST_DATA_TOURIST_TYPE
        client.get(url, object:
            AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val dataArray = jsonObject.getJSONArray("data_tourist")
                    Log.d("result", result.toString())
                    for (i in 0 until dataArray.length()) {
                        val dataItem = dataArray.getJSONObject(i)
                        Log.d("dataItem", i.toString())
                        val no=i+1
                        val touristDataType=dataItem.getString("tourist_data_type")
                        val idTouristDataType=dataItem.getString("id_tourist_data_type")
                        val dataType=dataItem.getString("data_type")

                        val dataTouristItem=DataTouristTypeItem(
                            no.toString(),
                            touristDataType,
                            idTouristDataType,
                            dataType
                        )
                        listItems.add(dataTouristItem)
                    }
                    listTouristDataType.postValue(listItems)
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
    fun setTouristData(
        id_tourist_data_type:Int
    ){
        val listDataTourist = ArrayList<DataTouristItem>()
        val client = AsyncHttpClient()
        client.get("http://192.168.100.6/skripsi-rest-forecasting/api/tourist?id_tourist_data_type=$id_tourist_data_type",object:
            AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val dataArray = jsonObject.getJSONArray("data_tourist")
                    Log.d("resultTouristData", result.toString())
                    Log.d("setTouristData", dataArray.toString())
                    Log.d("setTouristData", dataArray.length().toString())
                    for (i in 0 until dataArray.length()) {
                        val dataItem = dataArray.getJSONObject(i)
                        Log.d("dataItem", i.toString())
                        val no=i+1
                        val month=dataItem.getString("month")
                        val T=dataItem.getString("t")
                        val idDataPengunjung=dataItem.getString("id_data_pengunjung")
                        val year=dataItem.getString("year")
                        val dataPengunjung=dataItem.getString("data_pengunjung")
                        val idTouristDataType=dataItem.getString("id_data_pengunjung")
                        val dataTouristItem=DataTouristItem(
                            no.toString(),
                            month,
                            T,
                            idDataPengunjung,
                            year,
                            dataPengunjung,
                            idTouristDataType
                        )
                        listDataTourist.add(dataTouristItem)
                    }
                    listTouristData.postValue(listDataTourist)
                    Log.d("Dataaaa", listDataTourist.toString())
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
    fun getTouristDataType(): LiveData<ArrayList<DataTouristTypeItem>> {
        return listTouristDataType
    }
    fun getTouristData(): LiveData<ArrayList<DataTouristItem>> {
        return listTouristData
    }
}