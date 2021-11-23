package com.yusril.skripsi_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.response.DataEvaluationBy
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.response.EvaluationResponseBy
import com.yusril.skripsi_app.ui.TouristDataType.viewmodel.TouristDataTypeViewModel
import com.yusril.skripsi_app.viewmodel.EvaluationModel.Companion.URL_EVALUATION
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class EvaluationModel: ViewModel() {
    companion object {
        private const val URL_EVALUATION = BuildConfig.URL_EVALUATION
    }
    val listEvaluationBy = MutableLiveData<ArrayList<DataEvaluationBy>>()
    fun setEvaluationBy(
        id_tourist_data_type:Int,
        id_method_type:Int
    ){
        val listItems = ArrayList<DataEvaluationBy>()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.put("id_tourist_data_type", id_tourist_data_type)
        params.put("id_method_type", id_method_type)
        val url= "$URL_EVALUATION?id_tourist_data_type=$id_tourist_data_type&id_method_type=$id_method_type"
//        val url="http://192.168.100.6/skripsi-rest-forecasting/api/Evaluation?id_tourist_data_type=1&id_method_type=1"
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
                    val data=jsonObject["data_evaluation"]
                    val dataArray = jsonObject.getJSONArray("data_evaluation")
                    Log.d("setEvaluationBy1", result.toString())
                    Log.d("setEvaluationBy2", jsonObject.toString())
                    Log.d("setEvaluationBy3",  data.toString())
                    Log.d("setEvaluationBy4",  dataArray.toString())
                    for (i in 0 until dataArray.length()) {
                        val dataItem = dataArray.getJSONObject(i)
                        Log.d("setEvaluationBy5",  dataItem.toString())
                        val idErrorMeasurement=dataItem.getString("id_error_measurement")
                        val rsfe=dataItem.getString("rsfe")
                        val mad=dataItem.getString("mad")
                        val mape=dataItem.getString("mape")
                        val ts=dataItem.getString("ts")
                        val idTouristDataType=dataItem.getString("id_tourist_data_type")
                        val idMethodType=dataItem.getString("id_method_type")
                        val dataTouristItem=DataEvaluationBy(
                            mad,
                            idErrorMeasurement,
                            rsfe,
                            idMethodType,
                            idTouristDataType,
                            mape,
                            ts
                        )
                        listItems.add(dataTouristItem)
}
                    listEvaluationBy.postValue(listItems)
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

    fun getEvaluationBy(): LiveData<ArrayList<DataEvaluationBy>> {
        return listEvaluationBy
    }
}