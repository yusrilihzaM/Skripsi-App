package com.yusril.skripsi_app.ui.aditif.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yusril.skripsi_app.BuildConfig
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.adapter.DataTypeListAdapter
import com.yusril.skripsi_app.adapter.ManualListAdapter
import com.yusril.skripsi_app.databinding.ActivityAditifManualBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.datatourist.viewmodel.DataTouristViewModel
import com.yusril.skripsi_app.viewmodel.ManualModel


class AditifManualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAditifManualBinding
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private lateinit var manualModel: ManualModel
    companion object {
        private const val URL_MANUAL = BuildConfig.URL_MANUAL
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aditif_manual)
        binding = ActivityAditifManualBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        touristDataType= intent.getStringExtra("touristDataType").toString()
        idTouristDataType= intent.getIntExtra("idTouristDataType",0)
        supportActionBar?.title=getString(R.string.perhitungan)
        manualModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ManualModel::class.java)
        binding.title.text=getString(R.string.hasil_perhitungan_dekomposisi_aditif)+touristDataType
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.visibility=View.INVISIBLE
        binding.webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                binding.spinKit.visibility = View.GONE
                binding.webView.visibility=View.VISIBLE
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                Toast.makeText(this@AditifManualActivity, message, Toast.LENGTH_LONG).show()
                result.confirm()
                return true
            }
        }
        binding.webView.loadUrl("${URL_MANUAL}/$idTouristDataType/1")
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