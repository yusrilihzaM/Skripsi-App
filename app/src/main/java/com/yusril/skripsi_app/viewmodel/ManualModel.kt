package com.yusril.skripsi_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.response.DataManualItem
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class ManualModel: ViewModel() {
    companion object {
        private const val URL_MANUAL = BuildConfig.URL_MANUAL
    }
    val listManualBy = MutableLiveData<ArrayList<DataManualItem>>()
    fun setEvaluationBy(
        id_tourist_data_type:Int,
        id_method_type:Int
    ){
        val listItems = ArrayList<DataManualItem>()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.put("id_tourist_data_type", id_tourist_data_type)
        params.put("id_method_type", id_method_type)
        val url= "$URL_MANUAL?id_tourist_data_type=$id_tourist_data_type&id_method_type=$id_method_type"
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
                    val dataArray = jsonObject.getJSONArray("data_manual")

                    Log.d("setEvaluationBy",  jsonObject.toString())
                    Log.d("setEvaluationBy",  dataArray.toString())
                    Log.d("setEvaluationBy",  dataArray.length().toString())
                    for (i in 0 until dataArray.length()) {
                        val no=i+1
                        val dataItem = dataArray.getJSONObject(i)
                        Log.d("setEvaluationBy",  dataItem.toString())
                        val mad: String? = dataItem.getString("mad")
                        val T: String? =dataItem.getString("t")
                        val unadjusted: String? = dataItem.getString("unadjusted")
                        val dataPengunjung: String? = dataItem.getString("data_pengunjung")
                        val smoothed: String? = dataItem.getString("smoothed")
                        val adjusted: String? = dataItem.getString("adjusted")
                        val seasonalIndex: String? = dataItem.getString("seasonal_index")
                        val mape: String? = dataItem.getString("mape")
                        val error: String? = dataItem.getString("error")
                        val ctdma: String? = dataItem.getString("ctdma")
                        val ratio: String? = dataItem.getString("ratio")
                        val dataManualItem=DataManualItem(
                            no.toString(),
                           mad,
                            T,
                            unadjusted,
                            dataPengunjung,
                            smoothed,
                            adjusted,
                            seasonalIndex,
                            mape,
                            error,
                            ctdma,
                            ratio
                        )
                        listItems.add(dataManualItem)
                        }
                    Log.d("setEvaluationBy",  listItems.size.toString())
                    listManualBy.postValue(listItems)
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
                Log.d("onFailuresetEvaluationBy", error.toString())
            }
        }
        )
    }

    fun getManualBy(): LiveData<ArrayList<DataManualItem>> {
        return listManualBy
    }
}