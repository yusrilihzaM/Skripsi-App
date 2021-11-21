package com.yusril.skripsi_app.ui.home.fragment

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.adapter.HomeListAdapter
import com.yusril.skripsi_app.databinding.FragmentHomeBinding
import com.yusril.skripsi_app.entity.Menu
import com.yusril.skripsi_app.ui.TouristDataType.activity.TouristDataTypeActivity
import com.yusril.skripsi_app.ui.calculate.activity.CalculateActivity
import com.yusril.skripsi_app.ui.datatourist.activity.DataTouristAttractionActivity


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataTitle: Array<String>
    private lateinit var dataIc: TypedArray
    private lateinit var homeListAdapter: HomeListAdapter
    private var list: ArrayList<Menu> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeBinding.bind(view)

        showMenu()
    }
    private  fun showMenu(){

        binding.rvMenu.setHasFixedSize(true)
        binding.rvMenu.layoutManager= GridLayoutManager(context,3)
        list.addAll(getListMenu())
        homeListAdapter = HomeListAdapter(list)
        binding.rvMenu.adapter=homeListAdapter

        homeListAdapter.setOnItemClickCallback(object :HomeListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Menu) {
                val intent: Intent
                when(data.title){
                    getString(R.string.menu_data)->{
                        intent= Intent(context, DataTouristAttractionActivity::class.java)
                        startActivity(intent)

                    }

                    getString(R.string.place_data)->{
                        intent= Intent(context, TouristDataTypeActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    getString(R.string.menu_calculate)->{
                        intent= Intent(context, CalculateActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
        })
    }
    private fun getListMenu(): ArrayList<Menu> {
        val listMenu= ArrayList<Menu>()
        dataTitle = resources.getStringArray(R.array.data_title_menu)
        dataIc = resources.obtainTypedArray(R.array.data_ic_menu)
        for(position in dataTitle.indices){
            val menu= Menu(
                dataTitle[position],
                dataIc.getResourceId(position, -1)
            )
            listMenu.add(menu)
        }
        return listMenu
    }
}