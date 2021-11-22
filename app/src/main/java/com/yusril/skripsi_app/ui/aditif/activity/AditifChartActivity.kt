package com.yusril.skripsi_app.ui.aditif.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityAditifChartBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.aditif.activity.AditifChartActivity.Companion.URL_CHART

class AditifChartActivity : AppCompatActivity() {
    companion object {
        private const val URL_CHART = BuildConfig.URL_CHART
        const val EXTRA_DATA_CHART_ADITIF = "extra_data_chart"
    }
    private lateinit var binding: ActivityAditifChartBinding
    private var no:Int = 0
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private var dataType:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aditif_chart)
        binding = ActivityAditifChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.useWideViewPort = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        touristDataType= intent.getStringExtra("touristDataType").toString()
        idTouristDataType= intent.getIntExtra("idTouristDataType",0)

        supportActionBar?.title=getString(R.string.grafik)+" "+getString(R.string.aditif)+"-"+":"+touristDataType
        binding.webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                binding.spinKit.visibility = View.INVISIBLE
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                Toast.makeText(this@AditifChartActivity, message, Toast.LENGTH_LONG).show()
                result.confirm()
                return true
            }
        }
        binding.webView.loadUrl("$URL_CHART$idTouristDataType/1")
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332->{
                this.finish()
                true
            }
            else -> true
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}