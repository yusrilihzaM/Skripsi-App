package com.yusril.skripsi_app.ui.TouristDataType.viewmodel

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.entity.Status
import com.yusril.skripsi_app.response.DataTouristTypeItem

import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TouristDataTypeViewModel: ViewModel() {
    companion object {
        private const val URL_DATA_TOURIST_TYPE_PLACE = BuildConfig.URL_DATA_TOURIST_TYPE_PLACE
        private const val URL_POST_DATA_TOURIST_TYPE = BuildConfig.URL_DATA_TOURIST_TYPE

    }
    val listTouristDataType = MutableLiveData<ArrayList<DataTouristTypeItem>>()
    var statusAddTouristDataType = MutableLiveData<ArrayList<Status>>()
    var statusEditTouristDataType = MutableLiveData<ArrayList<Status>>()
    var statusDeleteTouristDataType = MutableLiveData<ArrayList<Status>>()
    fun setTouristDataType(){
        val listItems = ArrayList<DataTouristTypeItem>()
        val client = AsyncHttpClient()
        val url=URL_DATA_TOURIST_TYPE_PLACE
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

    fun postsetTouristDataType(
        tourist_data_type:String
    ){
        val client = AsyncHttpClient()
        val params = RequestParams()
        val listItems = ArrayList<Status>()
        params.put("tourist_data_type", tourist_data_type)
        params.put("data_type", "tempat wisata")
        client.post(URL_POST_DATA_TOURIST_TYPE,params,object :AsyncHttpResponseHandler(){
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
                    statusAddTouristDataType.postValue(listItems)
                    Log.d("zzz", status.toString())
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
    fun putsetTouristDataType(
        tourist_data_type:String,
        id_tourist_data_type:Int

    ){
        val client = AsyncHttpClient()
        val params = RequestParams()
        val listItems = ArrayList<Status>()
        params.put("id_tourist_data_type", id_tourist_data_type)
        params.put("tourist_data_type", tourist_data_type)
        params.put("data_type", "tempat wisata")
        client.put(URL_POST_DATA_TOURIST_TYPE,params,object :AsyncHttpResponseHandler(){
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
                    statusEditTouristDataType.postValue(listItems)
                    Log.d("zzz", status.toString())
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
    fun deletesetTouristDataType(
        id_tourist_data_type:Int
    ){
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.put("id_tourist_data_type", id_tourist_data_type)
        val listItems = ArrayList<Status>()
        client.put(URL_DATA_TOURIST_TYPE_PLACE,params,object :AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                Log.d("onSuccess", "Data Berhasil di Dihapus")
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val status_data:Boolean= jsonObject["status"] as Boolean
                    val status=Status(
                        status_data
                    )
                    listItems.add(status)
                    statusDeleteTouristDataType.postValue(listItems)
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
                Log.d("onFailure", "Data Gagal di Dihapus")
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

    fun getTouristDataType(): LiveData<ArrayList<DataTouristTypeItem>> {
        return listTouristDataType
    }
    fun getStatusAddTouristDataType(): LiveData<ArrayList<Status>> {
        return statusAddTouristDataType
    }
    fun getStatusEditTouristDataType(): LiveData<ArrayList<Status>> {
        return statusEditTouristDataType
    }
    fun getStatusDelTouristDataType(): LiveData<ArrayList<Status>> {
        return statusDeleteTouristDataType
    }


}