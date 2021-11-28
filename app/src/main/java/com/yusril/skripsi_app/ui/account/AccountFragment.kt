package com.yusril.skripsi_app.ui.account

import android.app.Activity
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.thecode.aestheticdialogs.*
import com.yusril.login_pref.helper.Constant
import com.yusril.login_pref.helper.PrefrencesHelper
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.adapter.HomeListAdapter
import com.yusril.skripsi_app.adapter.MenuSettingsListAdapter
import com.yusril.skripsi_app.databinding.FragmentAccountBinding
import com.yusril.skripsi_app.entity.MenuForecast
import com.yusril.skripsi_app.ui.login.LoginActivity
import com.yusril.skripsi_app.ui.multiplikatif.activity.MultiplikatifDataTypeActivity


class AccountFragment : Fragment() {
    private lateinit var menuSettingsListAdapter: MenuSettingsListAdapter
    private lateinit var binding: FragmentAccountBinding
    private lateinit var dataTitle: Array<String>
    private lateinit var dataSubTitle: Array<String>
    private lateinit var dataIc: TypedArray
    private lateinit var homeListAdapter: HomeListAdapter
    lateinit var prefrencesHelper: PrefrencesHelper
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
        prefrencesHelper= context?.let { PrefrencesHelper(it) }!!
        showMenu()

        binding.name.text=prefrencesHelper.getString(Constant.PREFS_IS_EMAIL)
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


                    }
                    getString(R.string.keluar)->{
               prefrencesHelper.clear()
                        dialog(
                            "Success",
                            getString(R.string.keluar),
                            getString(R.string.berhasil)
                        )


                    }
                }
            }
        })
    }
    private fun dialog(
        type:String,
        title:String,
        message:String
    ){
        if(type=="Success"){
            activity?.let {
                AestheticDialog.Builder(it, DialogStyle.TOASTER, DialogType.SUCCESS)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setDarkMode(false)
                    .setGravity(Gravity.TOP)
                    .setAnimation(DialogAnimation.SLIDE_UP)
                    .setOnClickListener(object : OnDialogClickListener {
                        override fun onClick(dialog: AestheticDialog.Builder) {
                            dialog.dismiss()
                            moveIntent()
                            it.finish()
                            //actions...
                        }
                    })
                    .show()
            }
        }else{
            activity?.let {
                AestheticDialog.Builder(it, DialogStyle.TOASTER, DialogType.ERROR)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setDarkMode(false)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .setOnClickListener(object : OnDialogClickListener {
                        override fun onClick(dialog: AestheticDialog.Builder) {
                            dialog.dismiss()
                            //actions...
                        }
                    })
                    .show()
            }
        }
    }
    private  fun moveIntent(){
        startActivity(Intent(context,LoginActivity::class.java))
        activity?.finish()
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