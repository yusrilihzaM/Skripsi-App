package com.yusril.skripsi_app.ui.calculate.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.thecode.aestheticdialogs.*
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityCalculateBinding
import com.yusril.skripsi_app.databinding.ActivityDataTouristAttractionBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.calculate.ViewModel.CalculateVIewModel
import com.yusril.skripsi_app.ui.datatourist.activity.AddDataTouristActivity
import com.yusril.skripsi_app.ui.datatourist.activity.DataTouristActivity
import com.yusril.skripsi_app.ui.datatourist.viewmodel.DataTouristViewModel
import com.yusril.skripsi_app.ui.main.MainActivity

class CalculateActivity : AppCompatActivity() {
    private lateinit var calculateVIewModel: CalculateVIewModel
    private lateinit var binding: ActivityCalculateBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)
        setContentView(R.layout.activity_data_tourist_attraction)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title=getString(R.string.hitung_model_peramalan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        calculateVIewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            CalculateVIewModel::class.java)

        calculateVIewModel.setCountData()
        calculateVIewModel.getStatusCountData().observe(this,{data->
            binding.status.visibility=View.VISIBLE
            binding.spinKit.visibility=View.VISIBLE
            if(data[0].countData.toString().toInt()>=0){
                binding.status.setBackgroundColor(R.color.green)
                binding.status.text=getString(R.string.modeldone)
            }else{
                binding.status.setBackgroundColor(R.color.red)
                binding.status.text=getString(R.string.notifcount0)

            }
        })

        binding.btnSubmit.setOnClickListener {
            calculateVIewModel.setCalculate()
            calculateVIewModel.getStatusCalculate().observe(this,{data->
                if (data[0].status){
                    dialog("1"
                    ,getString(R.string.hitung_model_peramalan)
                    ,getString(R.string.succacalculate)
                    )
                }
                else{
                    dialog("2"
                        ,getString(R.string.hitung_model_peramalan)
                        ,getString(R.string.fail))
                }
            })
        }
    }
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater=menuInflater
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332->{
                startActivity(Intent(this@CalculateActivity, MainActivity::class.java))
                this.finish()
                true
            }
            else -> true
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun dialog(
        type:String,
        title:String,
        message:String
    ){
        if(type=="1"){
            AestheticDialog.Builder(this@CalculateActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setDarkMode(false)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(object : OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()
                        pindah()
                        finish()
                        //actions...
                    }
                })
                .show()
        }else{
            AestheticDialog.Builder(this@CalculateActivity, DialogStyle.FLAT, DialogType.ERROR)
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
    fun pindah(){

        val intent = Intent(this@CalculateActivity,
            CalculateActivity::class.java)

        startActivity(intent)
    }
}