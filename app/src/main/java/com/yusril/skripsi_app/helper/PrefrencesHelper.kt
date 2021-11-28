package com.yusril.login_pref.helper

import android.content.Context
import android.content.SharedPreferences
import com.yusril.skripsi_app.ui.account.AccountFragment

class PrefrencesHelper(context: Context) {
    private val PREFS_NAME="sharedprefskripsi"
    private val sharedPreferences:SharedPreferences
    val editor:SharedPreferences.Editor

    init {
        sharedPreferences=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        // aktifkan editor
        editor=sharedPreferences.edit()
    }
    //menyimpan data

    fun put(key:String,value:String){
        editor.putString(key, value)
            .apply()
    }

    fun getString(key: String):String?{
        return sharedPreferences.getString(key,"")!!
    }

    fun put(key:String,value:Boolean){
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String):Boolean{
        return sharedPreferences.getBoolean(key,false)
    }
    fun clear() {
        editor.clear()
            .apply()
    }
}