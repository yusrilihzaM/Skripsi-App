package com.yusril.skripsi_app.ui.splashscreen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.ui.main.MainActivity

class SplashActivity : Activity() {
    companion object{
        const val TIME:Long=3000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({ moveActivity() }, TIME)
    }
    private fun moveActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}