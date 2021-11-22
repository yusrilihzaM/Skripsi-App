package com.yusril.skripsi_app.ui.multiplikatif.activity

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
import com.yusril.skripsi_app.databinding.ActivityMultiplikatifDataTypeBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.datatourist.viewmodel.DataTouristViewModel
import com.yusril.skripsi_app.ui.main.MainActivity

class MultiplikatifDataTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultiplikatifDataTypeBinding
    private lateinit var dataTouristViewModel: DataTouristViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var dataTypeListAdapter: DataTypeListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplikatif_data_type)
        binding = ActivityMultiplikatifDataTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title="Multiplikatif: "+getString(R.string.data_tempat_wisata)
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
//                    val intent = Intent(this@TouristDataTypeActivity,
//                        EditTouristDataTypeMainActivity::class.java)
//                    intent.putExtra(EditTouristDataTypeMainActivity.EXTRA_DATA, dataType)
//                    startActivity(intent)
//                    finish()
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
                startActivity(Intent(this@MultiplikatifDataTypeActivity, MainActivity::class.java))
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