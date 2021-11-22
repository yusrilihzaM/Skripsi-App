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
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.adapter.DataTypeListAdapter
import com.yusril.skripsi_app.databinding.ActivityAditifDataTypeBinding
import com.yusril.skripsi_app.databinding.ActivityTouristDataTypeBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.TouristDataType.activity.AddTouristDataTypeActivity
import com.yusril.skripsi_app.ui.TouristDataType.activity.EditTouristDataTypeMainActivity
import com.yusril.skripsi_app.ui.TouristDataType.viewmodel.TouristDataTypeViewModel
import com.yusril.skripsi_app.ui.aditif.activity.MenuAditifActivity.Companion.EXTRA_DATA_FORECASTING
import com.yusril.skripsi_app.ui.datatourist.viewmodel.DataTouristViewModel
import com.yusril.skripsi_app.ui.main.MainActivity

class AditifDataTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAditifDataTypeBinding
    private lateinit var dataTouristViewModel: DataTouristViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var dataTypeListAdapter: DataTypeListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aditif_data_type)
        binding = ActivityAditifDataTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title="Aditif: "+getString(R.string.data_tempat_wisata)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataTouristViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DataTouristViewModel::class.java)
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
        dataTouristViewModel.setTouristDataType()

        dataTouristViewModel.getTouristDataType().observe(this,{dataItems->
            Log.d("aaa", dataItems.toString())

            showShimmer(false)
            dataTypeListAdapter= DataTypeListAdapter(dataItems)
            binding.rvList.adapter=dataTypeListAdapter
            swipeContainer.isRefreshing = false
            dataTypeListAdapter.setOnItemClickCallback(object :DataTypeListAdapter.OnItemClickCallback{
                override fun onItemClicked(dataType: DataTouristTypeItem) {
                    val intent = Intent(this@AditifDataTypeActivity,
                        MenuAditifActivity::class.java)
                    intent.putExtra(EXTRA_DATA_FORECASTING, dataType)
                    startActivity(intent)
                    finish()
                }

            })
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
                startActivity(Intent(this@AditifDataTypeActivity, MainActivity::class.java))
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