package com.yusril.skripsi_app.ui.datatourist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.kal.rackmonthpicker.MonthType
import com.kal.rackmonthpicker.RackMonthPicker
import com.thecode.aestheticdialogs.*
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityAddDataTouristBinding
import com.yusril.skripsi_app.databinding.ActivityEditDataTouristBinding
import com.yusril.skripsi_app.response.DataTouristItem
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.TouristDataType.activity.EditTouristDataTypeMainActivity
import com.yusril.skripsi_app.ui.datatourist.activity.AddDataTouristActivity.Companion.EXTRA_DATA_TOURIST_TYPE
import com.yusril.skripsi_app.ui.datatourist.viewmodel.DataTouristViewModel
import java.util.*

class EditDataTouristActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA_TOURIST_TYPE_EDIT = "extra_data_tourist_type"
        const val EXTRA_DATA_TOURIST_EDIT = "extra_data_tourist_edit"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private lateinit var binding: ActivityEditDataTouristBinding
    private var no:Int = 0
    private var idDataPengunjung:Int = 0
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private var dataType:String = ""
    private lateinit var dataTouristViewModel: DataTouristViewModel
    private var txtYearMonth =""
    private var dataMonth:Int=0
    private var dataYear:Int=0
    private var dataMonthLast:Int=0
    private var dataYearLast:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data_tourist)
        binding = ActivityEditDataTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataTouristViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DataTouristViewModel::class.java)
        val data=intent.getParcelableExtra<DataTouristTypeItem>(EXTRA_DATA_TOURIST_TYPE_EDIT) as DataTouristTypeItem
        no=data.no?.toInt()!!
        touristDataType= data.touristDataType.toString()
        idTouristDataType= data.idTouristDataType?.toInt()!!
        dataType= data.dataType.toString()
        supportActionBar?.title=touristDataType

        val dataTourist=intent.getParcelableExtra<DataTouristItem>(EXTRA_DATA_TOURIST_EDIT) as DataTouristItem
        binding.edtJumlah.setText(dataTourist.dataPengunjung)
        binding.edtJumlah.setText(dataTourist.dataPengunjung)
        dataMonth=dataTourist.month.toString().toInt()
        dataYear=dataTourist.year.toString().toInt()
        idDataPengunjung=dataTourist.idDataPengunjung.toString().toInt()
        binding.edtTahun.setText("$dataMonth / $dataYear")
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
                        dataTouristViewModel.putsetTouristData(idDataPengunjung,dataPengunjung,dataMonth,dataYear,idTouristDataType)
                        dataTouristViewModel.getStatusEditTouristData().observe(this,{dataItems->
                            if (dataItems[0].status){
                                dialog(
                                    getString(R.string.suc),
                                    getString(R.string.data_tourist)+" $touristDataType",
                                    getString(R.string.succput)
                                )
                            }
                            else{
                                dialog(
                                    getString(R.string.fail),
                                    getString(R.string.data_tourist)+" $touristDataType",
                                    getString(R.string.failput)
                                )
                            }
                        })

                    }
        }
    }

    private fun delData(type: Int) {
        val isDialogClose = type == EditTouristDataTypeMainActivity.ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.delete_tourist)
        } else {
            dialogMessage = getString(R.string.delete_question_tourist)
            dialogTitle = getString(R.string.delete_tourist)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    dataTouristViewModel.deletesetTouristData(idDataPengunjung)
                    dataTouristViewModel.getStatusDelTouristDataType().observe(this,{dataItems->
                        if (dataItems[0].status){
                            dialog(
                                getString(R.string.suc),
                                getString(R.string.data_tourist)+" $touristDataType",
                                getString(R.string.succdel)
                            )
                        }
                        else{
                            dialog(
                                getString(R.string.fail),
                                getString(R.string.data_tourist)+" $touristDataType",
                                getString(R.string.faildel)
                            )
                        }
                    })

                }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.black))
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.red))
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.detail_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.del -> {

                delData(
                    ALERT_DIALOG_DELETE)
                true
            }
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
            AestheticDialog.Builder(this@EditDataTouristActivity, DialogStyle.FLAT, DialogType.SUCCESS)
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
            AestheticDialog.Builder(this@EditDataTouristActivity, DialogStyle.FLAT, DialogType.ERROR)
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
        val data1=intent.getParcelableExtra<DataTouristTypeItem>(AddDataTouristActivity.EXTRA_DATA_TOURIST_TYPE) as DataTouristTypeItem
        val intent = Intent(this@EditDataTouristActivity,
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