package com.yusril.skripsi_app.ui.TouristDataType.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.yusril.skripsi_app.adapter.DataTypeListAdapter
import com.yusril.skripsi_app.databinding.ActivityTouristDataTypeBinding
import com.yusril.skripsi_app.response.DataTouristItem
import com.yusril.skripsi_app.ui.TouristDataType.viewmodel.TouristDataTypeViewModel
import com.yusril.skripsi_app.ui.main.MainActivity

class TouristDataTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTouristDataTypeBinding
    private lateinit var touristDataTypeViewModel: TouristDataTypeViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var dataTypeListAdapter: DataTypeListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourist_data_type)
        binding = ActivityTouristDataTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title=getString(R.string.data_tempat_wisata)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        touristDataTypeViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TouristDataTypeViewModel::class.java)
        swipeContainer=binding.swipeContainer
        showList()
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = true
            showList()
            dataTypeListAdapter.notifyDataSetChanged()
        }
     
    }

    private fun showList(){
//        showShimmer(true)
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager= LinearLayoutManager(this)
        touristDataTypeViewModel.setTouristDataType()

        touristDataTypeViewModel.getTouristDataType().observe(this,{dataItems->
            Log.d("aaa", dataItems.toString())

            showShimmer(false)
            dataTypeListAdapter= DataTypeListAdapter(dataItems)
            binding.rvList.adapter=dataTypeListAdapter
            swipeContainer.isRefreshing = false
            dataTypeListAdapter.setOnItemClickCallback(object :DataTypeListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataTouristItem) {
                    Toast.makeText(this@TouristDataTypeActivity, data.dataType.toString(), Toast.LENGTH_SHORT).show()
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
        Log.d("item", "item i "+item.getItemId())
        return when (item.itemId) {
            R.id.add -> {
                startActivity(Intent(this, AddTouristDataTypeActivity::class.java))
                this.finish()
                true
            }
            16908332->{
                startActivity(Intent(this@TouristDataTypeActivity, MainActivity::class.java))
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
}