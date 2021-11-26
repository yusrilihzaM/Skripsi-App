package com.yusril.skripsi_app.ui.multiplikatif.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityAditifManualBinding
import com.yusril.skripsi_app.databinding.ActivityMultiplikatifManualBinding
import com.yusril.skripsi_app.ui.aditif.activity.AditifManualActivity
import com.yusril.skripsi_app.viewmodel.ManualModel

class MultiplikatifManualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultiplikatifManualBinding
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private lateinit var manualModel: ManualModel
    companion object {
        private const val URL_MANUAL = BuildConfig.URL_MANUAL
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplikatif_manual)
        binding = ActivityMultiplikatifManualBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        touristDataType= intent.getStringExtra("touristDataType").toString()
        idTouristDataType= intent.getIntExtra("idTouristDataType",0)
        supportActionBar?.title=getString(R.string.perhitungan)
        manualModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ManualModel::class.java)
        binding.title.text=getString(R.string.hasil_perhitungan_dekomposisi_multiplikatif)+touristDataType
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.visibility= View.INVISIBLE
        binding.webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                binding.spinKit.visibility = View.GONE
                binding.webView.visibility= View.VISIBLE
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                Toast.makeText(this@MultiplikatifManualActivity, message, Toast.LENGTH_LONG).show()
                result.confirm()
                return true
            }
        }
        binding.webView.loadUrl("${URL_MANUAL}/$idTouristDataType/2")
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