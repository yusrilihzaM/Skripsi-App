package com.yusril.skripsi_app.ui.splashscreen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.yusril.login_pref.helper.Constant
import com.yusril.login_pref.helper.PrefrencesHelper
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.ui.login.LoginActivity
import com.yusril.skripsi_app.ui.main.MainActivity

class SplashActivity : Activity() {
    companion object{
        const val TIME:Long=3000
    }
    lateinit var prefrencesHelper: PrefrencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        prefrencesHelper= PrefrencesHelper(this)
        Handler(Looper.getMainLooper()).postDelayed({
            if (prefrencesHelper.getBoolean(Constant.PREFS_IS_LOGIN)){
                moveHomeActivity()
            }
            else{
                moveLoginActivity()
            }
                                                    }, TIME)
    }
    private fun moveHomeActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun moveLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}