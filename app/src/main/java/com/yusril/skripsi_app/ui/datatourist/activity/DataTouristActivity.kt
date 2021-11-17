package com.yusril.skripsi_app.ui.datatourist.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.yusril.skripsi_app.adapter.DataTouristListAdapter
import com.yusril.skripsi_app.databinding.ActivityDataTouristBinding
import com.yusril.skripsi_app.response.DataTouristItem
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.datatourist.activity.AddDataTouristActivity.Companion.EXTRA_DATA_TOURIST_TYPE
import com.yusril.skripsi_app.ui.datatourist.activity.EditDataTouristActivity.Companion.EXTRA_DATA_TOURIST_TYPE_EDIT
import com.yusril.skripsi_app.ui.datatourist.viewmodel.DataTouristViewModel

class DataTouristActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA_TOURIST = "extra_data"
        const val EXTRA_DATA_TOURIST1 = "extra_data"
    }
    private lateinit var binding: ActivityDataTouristBinding
    private lateinit var dataTouristListAdapter: DataTouristListAdapter
    private var no:Int = 0
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private var dataType:String = ""

    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var dataTouristViewModel: DataTouristViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_tourist)
        binding = ActivityDataTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataTouristViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DataTouristViewModel::class.java)

        val data=intent.getParcelableExtra<DataTouristTypeItem>(EXTRA_DATA_TOURIST) as DataTouristTypeItem
        val data1=intent.getParcelableExtra<DataTouristTypeItem>(EXTRA_DATA_TOURIST1) as DataTouristTypeItem
        if(data!=null){
            no=data.no?.toInt()!!
            touristDataType= data.touristDataType.toString()
            idTouristDataType= data.idTouristDataType?.toInt()!!
            dataType= data.dataType.toString()
            supportActionBar?.title=touristDataType
        }
        if(data1!=null){
            no=data1.no?.toInt()!!
            touristDataType= data1.touristDataType.toString()
            idTouristDataType= data1.idTouristDataType?.toInt()!!
            dataType= data1.dataType.toString()
            supportActionBar?.title=touristDataType
        }



        swipeContainer=binding.swipeContainer
        showList()
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = true
            showList()
            dataTouristListAdapter.notifyDataSetChanged()
        }
    }

    private fun showList(){
        showShimmer(true)
        binding.rvListTourist.setHasFixedSize(true)
        binding.rvListTourist.layoutManager= LinearLayoutManager(this)
        dataTouristViewModel.setTouristData(idTouristDataType)

        dataTouristViewModel.getTouristData().observe(this,{dataItems->
            if (dataItems.size>0){
                showShimmer(false)
                showNodata(false)
                dataTouristListAdapter= DataTouristListAdapter(dataItems)
                binding.rvListTourist.adapter=dataTouristListAdapter

            }else{
                showShimmer(false)
                showNodata(true)
            }


            swipeContainer.isRefreshing = false
            dataTouristListAdapter.setOnItemClickCallback(object :DataTouristListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataTouristItem) {
                    val intent = Intent(this@DataTouristActivity,
                        EditDataTouristActivity::class.java)
                    intent.putExtra(EXTRA_DATA_TOURIST_TYPE_EDIT, data)
                    startActivity(intent)
                    finish()
                }

            })
        })
    }
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.data_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add->{
                Toast.makeText(this, "tambah", Toast.LENGTH_SHORT).show()
                val data=intent.getParcelableExtra<DataTouristTypeItem>(EXTRA_DATA_TOURIST) as DataTouristTypeItem
                val intent = Intent(this, AddDataTouristActivity::class.java)
                intent.putExtra(EXTRA_DATA_TOURIST_TYPE, data)
                startActivity(intent)
                true
            }
            16908332->{
                startActivity(Intent(this@DataTouristActivity, DataTouristAttractionActivity::class.java))
                this.finish()
                true
            }
            else -> true
        }
    }
    private fun showNodata(status:Boolean){
        if (status){
            binding.noData.visibility = View.VISIBLE
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
        }else{
            binding.noData.visibility = View.GONE
            binding.tvnodata.visibility = View.GONE
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DataTouristAttractionActivity::class.java))
        finish()
    }
    private fun showShimmer(boolean: Boolean){
        if(boolean){
            binding.shimmer.startShimmer()
            binding.shimmer.showShimmer(true)
        }
        else{
            binding.shimmer.stopShimmer()
            binding.shimmer.showShimmer(false)
            binding.shimmer.visibility= View.GONE
        }
    }
}