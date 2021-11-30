package com.yusril.skripsi_app.ui.datatourist.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kal.rackmonthpicker.MonthType
import com.kal.rackmonthpicker.RackMonthPicker
import com.thecode.aestheticdialogs.*
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityAddDataTouristBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.datatourist.viewmodel.DataTouristViewModel
import java.util.*


class AddDataTouristActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA_TOURIST_TYPE = "extra_data_tourist_type"
    }
    private lateinit var binding: ActivityAddDataTouristBinding
    private var no:Int = 0
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private var dataType:String = ""
    private lateinit var dataTouristViewModel: DataTouristViewModel
    private var txtYearMonth =""
    private var dataMonth:Int=0
    private var dataYear:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data_tourist)
        binding = ActivityAddDataTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataTouristViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DataTouristViewModel::class.java)
        val data=intent.getParcelableExtra<DataTouristTypeItem>(EXTRA_DATA_TOURIST_TYPE) as DataTouristTypeItem
        no=data.no?.toInt()!!
        touristDataType= data.touristDataType.toString()
        idTouristDataType= data.idTouristDataType?.toInt()!!
        dataType= data.dataType.toString()
        supportActionBar?.title=touristDataType

        binding.btnYearMonth.setOnClickListener {

            val today = Calendar.getInstance()
            val rackMonthPicker = RackMonthPicker(this)
                .setMonthType(MonthType.NUMBER)
                .setPositiveButton { month, startDate, endDate, year, monthLabel ->
                    dataMonth=month
                    dataYear=year
                    binding.edtTahun.setText("$month / $year")
                }.setColorTheme(R.color.white)
                .setNegativeButton { dialog -> dialog.dismiss() }
            rackMonthPicker.show();
        }
        binding.btnSubmit.setOnClickListener {
            binding.btnSubmit.startAnimation()
            if (binding.edtTahun.text.isEmpty() and binding.edtJumlah.text.isEmpty()){
                binding.edtTahun.error = getString(R.string.monthyearmust)
                binding.edtJumlah.error = getString(R.string.touristmust)
                status(true,getString(R.string.touristmonthyearmust))
            }else
                if (binding.edtTahun.text.isEmpty()){
                    binding.edtTahun.error = getString(R.string.monthyearmust)
                    status(true,getString(R.string.monthyearmust))
                }
                else
                    if (binding.edtJumlah.text.isEmpty() ){
                        binding.edtJumlah.error = getString(R.string.touristmust)
                        status(true,getString(R.string.touristmust))
                    }
            else{
                        val monthYear=binding.edtTahun
                        val dataPengunjung=binding.edtJumlah.text.toString().toInt()
                        dataTouristViewModel.postsetTouristData(dataPengunjung,dataMonth,dataYear,idTouristDataType)
                        dialog(
                          getString(R.string.suc),
                            getString(R.string.data_tourist)+" $touristDataType",
                            getString(R.string.succadd)
                        )
            }
        }
    }
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater=menuInflater
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332->{
                pindah()
                this.finish()
                true
            }
            else -> true
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        pindah()
        finish()
    }
    private fun dialog(
        type:String,
        title:String,
        message:String
    ){
        if(type=="Success"){
            AestheticDialog.Builder(this@AddDataTouristActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setDarkMode(false)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(object : OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()
                        binding.btnSubmit.revertAnimation()
                        pindah()
                        finish()
                        //actions...
                    }
                })
                .show()
        }else{
            AestheticDialog.Builder(this@AddDataTouristActivity, DialogStyle.FLAT, DialogType.ERROR)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setDarkMode(false)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(object : OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()
                        binding.btnSubmit.revertAnimation()
                        //actions...
                    }
                })
                .show()
        }
    }
    fun pindah(){
        val data1=intent.getParcelableExtra<DataTouristTypeItem>(EXTRA_DATA_TOURIST_TYPE) as DataTouristTypeItem
        val intent = Intent(this@AddDataTouristActivity,
            DataTouristActivity::class.java)
        intent.putExtra(DataTouristActivity.EXTRA_DATA_TOURIST1, data1)
        startActivity(intent)
    }
    fun status(muncul:Boolean, pesan:String){
        if(muncul){
            binding.status.visibility= View.VISIBLE
            binding.status.text=pesan
        }
    }
}