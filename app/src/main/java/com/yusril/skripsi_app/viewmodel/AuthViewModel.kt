package com.yusril.skripsi_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.entity.StatusMessage
import com.yusril.skripsi_app.response.DataUserItem
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class AuthViewModel : ViewModel() {
    companion object {
        private const val URL_LOGIN = BuildConfig.URL_LOGIN
    }
    val listStatusMessage = MutableLiveData<ArrayList<StatusMessage>>()
    val listDataLogin = MutableLiveData<ArrayList<DataUserItem>>()
    fun setStatusMessage(
        email:String,
        password:String
    ){
        val listItems1 = ArrayList<StatusMessage>()
        val listItems2 = ArrayList<DataUserItem>()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.put("email", email)
        params.put("password", password)
        val url=URL_LOGIN
        client.get(url,params,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val statusData:Boolean= jsonObject["status"] as Boolean

                    if (statusData){
                        val message="ok"
                        val status= StatusMessage(
                            true,
                            message
                        )
                        listItems1.add(status)
                        listStatusMessage.postValue(listItems1)
                    }else{
                        val message:String=jsonObject["message"].toString()
                        val status= StatusMessage(
                            statusData,
                            message
                        )
                        listItems1.add(status)
                        listStatusMessage.postValue(listItems1)

                    }
                    Log.d("setStatusMessage", listItems1.toString())

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
                Log.d("onFailuresetAuth", error.toString())
            }
        })
    }

    fun setLogin(
        email:String,
        password:String
    ){
        val listItems = ArrayList<DataUserItem>()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.put("email", email)
        params.put("password", password)
        val url=URL_LOGIN
        client.get(url,params,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val dataUser=jsonObject["data_user"]
                    val jsonUserObject = JSONObject(dataUser.toString())
                    Log.d("setLogin", result.toString())
                    Log.d("setLogin", jsonUserObject.toString())

                    val iduser=jsonUserObject["id_user"].toString()
                    val email=jsonUserObject["email"].toString()
                    val pass=jsonUserObject["password"].toString()
                    val dataUserItem= DataUserItem(
                        iduser,
                        email,
                        pass
                    )
                    listItems.add(dataUserItem)
                    listDataLogin.postValue(listItems)
                    Log.d("setLogin", listItems.toString())
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
                Log.d("onFailuresetAuth", error.toString())
            }
        })
    }

    fun getStatusMessage(): LiveData<ArrayList<StatusMessage>> {
        return listStatusMessage
    }
    fun getLogin(): LiveData<ArrayList<DataUserItem>> {
        return listDataLogin
    }
}