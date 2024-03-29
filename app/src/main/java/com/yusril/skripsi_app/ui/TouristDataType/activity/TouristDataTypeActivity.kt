package com.yusril.skripsi_app.ui.TouristDataType.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.adapter.DataTypeListAdapter
import com.yusril.skripsi_app.databinding.ActivityTouristDataTypeBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.TouristDataType.activity.EditTouristDataTypeMainActivity.Companion.EXTRA_DATA
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
        showShimmer(true)
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
                override fun onItemClicked(dataType: DataTouristTypeItem) {
                    val intent = Intent(this@TouristDataTypeActivity,EditTouristDataTypeMainActivity::class.java)
                    intent.putExtra(EXTRA_DATA, dataType)
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
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}