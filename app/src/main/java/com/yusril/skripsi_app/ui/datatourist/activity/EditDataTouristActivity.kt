package com.yusril.skripsi_app.ui.datatourist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yusril.skripsi_app.R

class EditDataTouristActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA_TOURIST_TYPE_EDIT = "extra_data_tourist_type"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data_tourist)
    }
}