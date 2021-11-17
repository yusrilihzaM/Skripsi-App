package com.yusril.skripsi_app.ui.TouristDataType.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.thecode.aestheticdialogs.*
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityEditTouristDataTypeMainBinding
import com.yusril.skripsi_app.response.DataTouristTypeItem
import com.yusril.skripsi_app.ui.TouristDataType.viewmodel.TouristDataTypeViewModel

class EditTouristDataTypeMainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private lateinit var binding: ActivityEditTouristDataTypeMainBinding
    private lateinit var touristDataTypeViewModel: TouristDataTypeViewModel
    private lateinit var edtTouristDataType:String
    private var no:Int = 0
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    private var dataType:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tourist_data_type_main)
        binding = ActivityEditTouristDataTypeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title=getString(R.string.edit_place)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        touristDataTypeViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TouristDataTypeViewModel::class.java)
        val data=intent.getParcelableExtra<DataTouristTypeItem>(EXTRA_DATA) as DataTouristTypeItem
        no=data.no?.toInt()!!
        touristDataType= data.touristDataType.toString()
        idTouristDataType= data.idTouristDataType?.toInt()!!
        dataType= data.dataType.toString()
        supportActionBar?.title=touristDataType
        binding.edtDataTouristType.setText(touristDataType)
        binding.btnSubmit.setOnClickListener {
            if (binding.edtDataTouristType.text.isEmpty() ){
                binding.edtDataTouristType.error = getString(R.string.alert_tempat_wisata)
                status(true,getString(R.string.alert_tempat_wisata))
            }else{
                edtTouristDataType=binding.edtDataTouristType.text.toString().trim()
                touristDataTypeViewModel.putsetTouristDataType(
                    edtTouristDataType,
                    idTouristDataType
                )
                touristDataTypeViewModel.getStatusEditTouristDataType().observe(this,{dataItems->

                    if (dataItems[0].status){
                        dialog("Success",getString(R.string.change_place))
                    }
                    else{
                        dialog("Gagal",getString(R.string.change_place))
                    }
                })

            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, TouristDataTypeActivity::class.java))
        finish()
    }
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.detail_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("item", "item i "+item.itemId)
        return when (item.itemId) {
            R.id.del -> {
                delData(ALERT_DIALOG_DELETE)
                true
            }
            16908332->{
                startActivity(Intent(this@EditTouristDataTypeMainActivity, TouristDataTypeActivity::class.java))
                this.finish()
                true
            }
            else -> true
        }
    }
    private fun delData(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.delete_question)
        } else {
            dialogMessage = getString(R.string.delete_question_place)
            dialogTitle = getString(R.string.delete_place)
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
                    touristDataTypeViewModel.deletesetTouristDataType(idTouristDataType)
                    touristDataTypeViewModel.getStatusDelTouristDataType().observe(this,{dataItems->
                        if (dataItems[0].status){
                            dialog("Success",getString(R.string.delete_place))
                        }
                        else{
                            dialog("Gagal",getString(R.string.delete_place))
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
    private fun dialog(
        type:String,
        message:String
    ){
        if(type=="Success"){
            AestheticDialog.Builder(this@EditTouristDataTypeMainActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                .setTitle(message)
                .setMessage(getString(R.string.succsess))
                .setCancelable(false)
                .setDarkMode(false)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(object : OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()
                        startActivity(Intent(this@EditTouristDataTypeMainActivity, TouristDataTypeActivity::class.java))
                        finish()
                        //actions...
                    }
                })
                .show()
        }else{
            AestheticDialog.Builder(this@EditTouristDataTypeMainActivity, DialogStyle.FLAT, DialogType.ERROR)
                .setTitle(getString(R.string.add_new_place))
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
    fun status(muncul:Boolean, pesan:String){
        if(muncul){
            binding.status.visibility= View.VISIBLE
            binding.status.text=pesan
        }
    }
}