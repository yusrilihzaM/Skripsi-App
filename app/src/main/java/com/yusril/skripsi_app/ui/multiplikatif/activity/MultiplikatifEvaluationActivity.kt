package com.yusril.skripsi_app.ui.multiplikatif.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityAditifEvaluationBinding
import com.yusril.skripsi_app.databinding.ActivityMultiplikatifEvaluationBinding
import com.yusril.skripsi_app.viewmodel.EvaluationModel

class MultiplikatifEvaluationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultiplikatifEvaluationBinding
    private lateinit var evaluationModel: EvaluationModel
    private var touristDataType:String = ""
    private var idTouristDataType:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplikatif_evaluation)
        binding = ActivityMultiplikatifEvaluationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        evaluationModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            EvaluationModel::class.java)

        binding.spinKit.visibility = View.VISIBLE
        touristDataType= intent.getStringExtra("touristDataType").toString()
        idTouristDataType= intent.getIntExtra("idTouristDataType",0)
        supportActionBar?.title=getString(R.string.hasil_evaluasi_model_peramalan_multiplikatif)+"$idTouristDataType"
        binding.tvTitle.text=getString(R.string.hasil_evaluasi_model_peramalan_multiplikatif)+" $touristDataType"
        evaluationModel.setEvaluationBy(idTouristDataType,1)
        evaluationModel.getEvaluationBy().observe(this,{dataItem->
            Log.d("setEvaluationBy1", dataItem.toString())
            binding.spinKit.visibility = View.GONE
            binding.mape.text=dataItem[0].mape
            binding.mad.text=dataItem[0].mad
            binding.rsfe.text=dataItem[0].rsfe
            binding.ts.text=dataItem[0].ts
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332->{
                this.finish()
                true
            }
            else -> true
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}