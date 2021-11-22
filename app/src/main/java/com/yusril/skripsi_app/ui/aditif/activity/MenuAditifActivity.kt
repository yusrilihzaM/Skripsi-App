package com.yusril.skripsi_app.ui.aditif.activity

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.adapter.HomeListAdapter
import com.yusril.skripsi_app.adapter.MenuForecastListAdapter
import com.yusril.skripsi_app.databinding.ActivityAditifDataTypeBinding
import com.yusril.skripsi_app.databinding.ActivityDataTouristBinding
import com.yusril.skripsi_app.databinding.ActivityMenuAditifBinding
import com.yusril.skripsi_app.entity.Menu
import com.yusril.skripsi_app.entity.MenuForecast
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.TouristDataType.activity.TouristDataTypeActivity
import com.yusril.skripsi_app.ui.calculate.activity.CalculateActivity
import com.yusril.skripsi_app.ui.datatourist.activity.DataTouristActivity
import com.yusril.skripsi_app.ui.datatourist.activity.DataTouristAttractionActivity
import com.yusril.skripsi_app.ui.datatourist.activity.EditDataTouristActivity
import com.yusril.skripsi_app.ui.forecastFuture.activity.ForecastFutureActivity
import com.yusril.skripsi_app.ui.main.MainActivity
import com.yusril.skripsi_app.ui.multiplikatif.activity.MultiplikatifDataTypeActivity

class MenuAditifActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuAditifBinding
    companion object {
        const val EXTRA_DATA_FORECASTING = "extra_data"
    }
    private var no:Int = 0
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private var dataType:String = ""
    private lateinit var dataTitle: Array<String>
    private lateinit var dataSubTitle: Array<String>
    private lateinit var dataIc: TypedArray
    private var list: ArrayList<MenuForecast> = arrayListOf()
    private lateinit var menuForecastListAdapter: MenuForecastListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_aditif)
        binding = ActivityMenuAditifBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val data=intent.getParcelableExtra<DataTouristTypeItem>(EXTRA_DATA_FORECASTING) as DataTouristTypeItem
        no=data.no?.toInt()!!
        touristDataType= data.touristDataType.toString()
        idTouristDataType= data.idTouristDataType?.toInt()!!
        dataType= data.dataType.toString()
        supportActionBar?.title=getString(R.string.aditif)+":"+touristDataType
        showMenu()
    }
    private  fun showMenu(){

        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager= LinearLayoutManager(this)
        list.addAll(getListMenu())
        menuForecastListAdapter = MenuForecastListAdapter(list)
        binding.rvList.adapter=menuForecastListAdapter

        menuForecastListAdapter.setOnItemClickCallback(object : MenuForecastListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: MenuForecast) {
                val intent: Intent
                when(data.title){
                    getString(R.string.menu_perhitungan)->{
//                        intent= Intent(this@MenuAditifActivity, DataTouristAttractionActivity::class.java)
//                        startActivity(intent)
                    }
                }
            }
        })
    }
    private fun getListMenu(): ArrayList<MenuForecast> {
        val listMenu= ArrayList<MenuForecast>()
        dataTitle = resources.getStringArray(R.array.data_title_menu_forecast)
        dataSubTitle = resources.getStringArray(R.array.data_sub_title_menu_forecast)
        dataIc = resources.obtainTypedArray(R.array.data_ic_forecast)
        for(position in dataTitle.indices){
            val menu= MenuForecast(
                dataTitle[position],
                dataSubTitle[position],
                dataIc.getResourceId(position, -1)
            )
            listMenu.add(menu)
        }
        return listMenu
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332->{
                startActivity(Intent(this@MenuAditifActivity, AditifDataTypeActivity::class.java))
                this.finish()
                true
            }
            else -> true
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AditifDataTypeActivity::class.java))
        finish()
    }
}