package com.yusril.skripsi_app.ui.TouristDataType.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.thecode.aestheticdialogs.*
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityAddTouristDataTypeBinding
import com.yusril.skripsi_app.databinding.ActivityTouristDataTypeBinding
import com.yusril.skripsi_app.ui.TouristDataType.viewmodel.TouristDataTypeViewModel
import com.yusril.skripsi_app.ui.main.MainActivity

class AddTouristDataTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTouristDataTypeBinding
    private lateinit var touristDataTypeViewModel: TouristDataTypeViewModel
    private lateinit var edtTouristDataType:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tourist_data_type)
        supportActionBar?.title=getString(R.string.add_new_place)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityAddTouristDataTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        touristDataTypeViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TouristDataTypeViewModel::class.java)
        binding.btnSubmit.setOnClickListener {
            if (binding.edtDataTouristType.text.isEmpty() ){
                binding.edtDataTouristType.error = getString(R.string.alert_tempat_wisata)
                status(true,getString(R.string.alert_tempat_wisata))
            }else{
                edtTouristDataType=binding.edtDataTouristType.text.toString().trim()
                touristDataTypeViewModel.postsetTouristDataType(edtTouristDataType)
                touristDataTypeViewModel.getStatusAddTouristDataType().observe(this,{dataItems->

                    if (dataItems[0].status){
                        AestheticDialog.Builder(this@AddTouristDataTypeActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                            .setTitle(getString(R.string.add_new_place))
                            .setMessage(getString(R.string.succsess))
                            .setCancelable(false)
                            .setDarkMode(false)
                            .setGravity(Gravity.CENTER)
                            .setAnimation(DialogAnimation.SHRINK)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                    startActivity(Intent(this@AddTouristDataTypeActivity, TouristDataTypeActivity::class.java))
                                    finish()
                                    //actions...
                                }
                            })
                            .show()

                    }else{
                        AestheticDialog.Builder(this@AddTouristDataTypeActivity, DialogStyle.FLAT, DialogType.ERROR)
                            .setTitle(getString(R.string.add_new_place))
                            .setMessage(getString(R.string.fail))
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
                })

            }

        }
    }
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("item", "item i "+item.itemId)
        return when (item.itemId) {
            16908332->{
                startActivity(Intent(this@AddTouristDataTypeActivity, TouristDataTypeActivity::class.java))
                this.finish()
                true
            }
            else -> true
        }
    }
    fun status(muncul:Boolean, pesan:String){
        if(muncul){
            binding.status.visibility= View.VISIBLE
            binding.status.text=pesan
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, TouristDataTypeActivity::class.java))
        finish()
    }
}