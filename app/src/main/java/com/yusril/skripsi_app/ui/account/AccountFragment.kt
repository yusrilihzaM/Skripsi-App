package com.yusril.skripsi_app.ui.account

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.adapter.HomeListAdapter
import com.yusril.skripsi_app.adapter.MenuForecastListAdapter
import com.yusril.skripsi_app.adapter.MenuSettingsListAdapter
import com.yusril.skripsi_app.databinding.FragmentAccountBinding
import com.yusril.skripsi_app.databinding.FragmentHomeBinding
import com.yusril.skripsi_app.entity.Menu
import com.yusril.skripsi_app.entity.MenuForecast
import com.yusril.skripsi_app.ui.TouristDataType.activity.TouristDataTypeActivity
import com.yusril.skripsi_app.ui.aditif.activity.AditifDataTypeActivity
import com.yusril.skripsi_app.ui.calculate.activity.CalculateActivity
import com.yusril.skripsi_app.ui.datatourist.activity.DataTouristAttractionActivity
import com.yusril.skripsi_app.ui.forecastFuture.activity.ForecastFutureActivity
import com.yusril.skripsi_app.ui.multiplikatif.activity.MultiplikatifDataTypeActivity


class AccountFragment : Fragment() {
    private lateinit var menuSettingsListAdapter: MenuSettingsListAdapter
    private lateinit var binding: FragmentAccountBinding
    private lateinit var dataTitle: Array<String>
    private lateinit var dataSubTitle: Array<String>
    private lateinit var dataIc: TypedArray
    private lateinit var homeListAdapter: HomeListAdapter
    private var list: ArrayList<MenuForecast> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentAccountBinding.bind(view)

        showMenu()
    }

    private  fun showMenu(){
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager= LinearLayoutManager(context)
        list.addAll(getListMenu())
        menuSettingsListAdapter=MenuSettingsListAdapter(getListMenu())
        binding.rvList.adapter=menuSettingsListAdapter

        menuSettingsListAdapter.setOnItemClickCallback(object :MenuSettingsListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: MenuForecast) {
                val intent: Intent
                when(data.title){
                    getString(R.string.ganti_bahasa)->{
                        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))

                    }
                    getString(R.string.tentang_aplikasi)->{
//                        intent= Intent(context, DataTouristAttractionActivity::class.java)
//                        startActivity(intent)

                    }
                    getString(R.string.keluar)->{
//                        intent= Intent(context, DataTouristAttractionActivity::class.java)
//                        startActivity(intent)

                    }
                }
            }
        })
    }
    private fun getListMenu(): ArrayList<MenuForecast> {
        val listMenu= ArrayList<MenuForecast>()
        dataTitle = resources.getStringArray(R.array.data_title_settings)
        dataSubTitle = resources.getStringArray(R.array.data_sub_title_settings)
        dataIc = resources.obtainTypedArray(R.array.data_ic_settings)
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
}