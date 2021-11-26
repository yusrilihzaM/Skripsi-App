package com.yusril.skripsi_app.ui.multiplikatif.activity

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityAditifChartBinding
import com.yusril.skripsi_app.databinding.ActivityMultiplikatifChartBinding
import com.yusril.skripsi_app.ui.aditif.activity.AditifChartActivity

class MultiplikatifChartActivity : AppCompatActivity() {
    companion object {
        private const val URL_CHART = BuildConfig.URL_CHART
        const val EXTRA_DATA_CHART_MULTIPLIKATIF = "extra_data_chart"
    }
    private lateinit var binding: ActivityMultiplikatifChartBinding
    private var no:Int = 0
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private var dataType:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplikatif_chart)
        binding = ActivityMultiplikatifChartBinding.inflate(layoutInflater)
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
                Toast.makeText(this@MultiplikatifChartActivity, message, Toast.LENGTH_LONG).show()
                result.confirm()
                return true
            }
        }
        binding.webView.loadUrl("${URL_CHART}$idTouristDataType/2")
    }
}