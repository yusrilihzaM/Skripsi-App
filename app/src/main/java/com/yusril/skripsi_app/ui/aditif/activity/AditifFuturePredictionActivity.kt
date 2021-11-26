package com.yusril.skripsi_app.ui.aditif.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.adapter.DataFutureListAdapter
import com.yusril.skripsi_app.adapter.DataTypeListAdapter
import com.yusril.skripsi_app.databinding.ActivityAditifDataTypeBinding
import com.yusril.skripsi_app.databinding.ActivityAditifFuturePredictionBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.forecastFuture.viewmodel.ForecastFutureModel
import com.yusril.skripsi_app.ui.main.MainActivity

class AditifFuturePredictionActivity : AppCompatActivity() {
    private lateinit var forecastFutureModel: ForecastFutureModel
    private lateinit var binding: ActivityAditifFuturePredictionBinding
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var dataFutureListAdapter: DataFutureListAdapter
    private var touristDataType:String = ""
    private var idMethod:Int = 1
    private var idTouristDataType:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aditif_future_prediction)
        binding = ActivityAditifFuturePredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        touristDataType= intent.getStringExtra("touristDataType").toString()
        idTouristDataType= intent.getIntExtra("idTouristDataType",0)
        supportActionBar?.title=getString(R.string.hasil_evaluasi_model_peramalan_aditif)+"$idTouristDataType"
        binding.title.text=getString(R.string.hasil_peramalan)+" $touristDataType"
        binding.ln.visibility=View.INVISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        forecastFutureModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ForecastFutureModel::class.java)
        swipeContainer=binding.swipeContainer
        forecastFutureModel.setStatusForecastFutureByTypeMethod(idTouristDataType,idMethod)
        forecastFutureModel.getStatusForecastFuture().observe(this,{dataItems->
            Log.d("dataItems", dataItems[0].status.toString())
            if (dataItems[0].status){
                showList()
                swipeContainer.setOnRefreshListener {
                    swipeContainer.isRefreshing = true
                    showList()
                    dataFutureListAdapter.notifyDataSetChanged()
                }
            }else{
                showShimmer(false)
                binding.ln.visibility=View.GONE
                binding.noData.visibility = View.VISIBLE
                binding.tvnodata.text=getString(R.string.not_found_data_future)
                binding.tvnodata.visibility = View.VISIBLE
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.nodatagif) // Replace with a valid url
                    .addListener(object : RequestListener<GifDrawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<GifDrawable?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: GifDrawable?,
                            model: Any?,
                            target: Target<GifDrawable?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource?.setLoopCount(1)
                            return false
                        }

                    })
                    .into(binding.noData)
            }
        })

    }
    private fun showList(){
        showShimmer(true)
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager= LinearLayoutManager(this)
        forecastFutureModel.setForecastFutureByTypeMethod(idTouristDataType,idMethod)
        forecastFutureModel.getForecastFutureByTypeMethod().observe(this,{dataItems->
            showShimmer(false)
            binding.ln.visibility=View.VISIBLE
            dataFutureListAdapter= DataFutureListAdapter(dataItems)
            binding.rvList.adapter=dataFutureListAdapter
            swipeContainer.isRefreshing = false

        })
    }
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater=menuInflater
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("item", "item i "+item.getItemId())
        return when (item.itemId) {
            16908332->{
                this.finish()
                true
            }
            else -> true
        }
    }

    private fun showShimmer(boolean: Boolean){
        if(boolean){
            binding.shimmer.startShimmer()
            binding.shimmer.showShimmer(true)
            binding.shimmer.visibility= View.VISIBLE
        }
        else{
            binding.shimmer.stopShimmer()
            binding.shimmer.showShimmer(false)
            binding.shimmer.visibility= View.GONE
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}