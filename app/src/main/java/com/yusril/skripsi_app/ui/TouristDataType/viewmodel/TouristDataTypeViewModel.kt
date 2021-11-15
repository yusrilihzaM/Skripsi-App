package com.yusril.skripsi_app.ui.TouristDataType.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.yusril.skripsi_app.api.ApiConfig
import com.yusril.skripsi_app.response.DataTouristItem
import com.yusril.skripsi_app.response.GetTouristDataTypeResponse
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class TouristDataTypeViewModel: ViewModel() {
    val listTouristDataType = MutableLiveData<ArrayList<DataTouristItem>>()

    fun setTouristDataType(){
        val listItems = ArrayList<DataTouristItem>()
        val client = AsyncHttpClient()
        val url="http://192.168.100.6/skripsi-rest-forecasting/api/TouristDataType"
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
                    Log.d("result", dataArray.toString())
                    for (i in 0 until dataArray.length()) {
                        val dataItem = dataArray.getJSONObject(i)
                        Log.d("dataItem", i.toString())
                        val no=i+1
                        val touristDataType=dataItem.getString("tourist_data_type")
                        val idTouristDataType=dataItem.getString("id_tourist_data_type")
                        val dataType=dataItem.getString("data_type")

                        val dataTouristItem=DataTouristItem(
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
    fun getTouristDataType(): LiveData<ArrayList<DataTouristItem>> {
        return listTouristDataType
    }

}