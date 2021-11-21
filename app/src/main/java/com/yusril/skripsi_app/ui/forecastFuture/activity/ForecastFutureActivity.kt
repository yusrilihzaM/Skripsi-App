package com.yusril.skripsi_app.ui.forecastFuture.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.thecode.aestheticdialogs.*
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityForecastFutureBinding
import com.yusril.skripsi_app.ui.calculate.ViewModel.CalculateVIewModel
import com.yusril.skripsi_app.ui.calculate.activity.CalculateActivity
import com.yusril.skripsi_app.ui.forecastFuture.viewmodel.ForecastFutureModel
import com.yusril.skripsi_app.ui.main.MainActivity

class ForecastFutureActivity : AppCompatActivity(){
    private lateinit var calculateVIewModel: CalculateVIewModel
    private lateinit var forecastFutureModel: ForecastFutureModel
    private lateinit var binding: ActivityForecastFutureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_future)
        binding = ActivityForecastFutureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title=getString(R.string.ramalperiodemasadepan)+getString(R.string.anda_dapat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        calculateVIewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            CalculateVIewModel::class.java)
        forecastFutureModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ForecastFutureModel::class.java)
        calculateVIewModel.setCountData()
        calculateVIewModel.getStatusCountData().observe(this,{data->
            binding.status.visibility= View.VISIBLE
            binding.spinKit.visibility= View.VISIBLE
            if(data[0].countData.toString().toInt()>=0){
                binding.status.setBackgroundResource(R.color.yellow)
                binding.status.text=getString(R.string.modeldone)
                binding.btnSubmit.setOnClickListener {
                    if(binding.edtPeriod.text.isEmpty()){
                        binding.edtPeriod.error = getString(R.string.edtperiodmust)
                    }else{
                        val period=binding.edtPeriod.text.toString().toInt()
                        forecastFutureModel.setPostForecastFuture(period)
                        forecastFutureModel.getStatusForecastFuture().observe(this,{dataItem->
                            if (dataItem[0].status){
                                dialog(
                                    "1",
                                    getString(R.string.ramalperiodemasadepan),
                                    getString(R.string.suc_future)
                                )
                            }
                            else{
                                dialog(
                                    getString(R.string.fail),
                                    getString(R.string.ramalperiodemasadepan),
                                    getString(R.string.fail_future_forecast)
                                )
                            }
                        })
                    }


                }
            }
            if(data[0].countData.toString().toInt()==0){
                binding.status.setBackgroundResource(R.color.red)
                binding.status.text=getString(R.string.fail_future)
                binding.btnSubmit.setOnClickListener {
                    dialog(
                        "2",
                        getString(R.string.titlef_forecast_future),
                        getString(R.string.fail_future)
                    )
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater=menuInflater
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332->{
                startActivity(Intent(this@ForecastFutureActivity, MainActivity::class.java))
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
            AestheticDialog.Builder(this@ForecastFutureActivity, DialogStyle.FLAT, DialogType.SUCCESS)
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
                    }
                })
                .show()
        }else{
            AestheticDialog.Builder(this@ForecastFutureActivity, DialogStyle.FLAT, DialogType.ERROR)
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
        val intent = Intent(this@ForecastFutureActivity,
            ForecastFutureActivity::class.java)
        startActivity(intent)
    }


}