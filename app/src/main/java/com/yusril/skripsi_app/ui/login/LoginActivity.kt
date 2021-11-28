package com.yusril.skripsi_app.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thecode.aestheticdialogs.*
import com.yusril.login_pref.helper.Constant
import com.yusril.login_pref.helper.PrefrencesHelper
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityLoginBinding
import com.yusril.skripsi_app.ui.datatourist.viewmodel.DataTouristViewModel
import com.yusril.skripsi_app.ui.main.MainActivity
import com.yusril.skripsi_app.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var prefrencesHelper: PrefrencesHelper
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var dataTouristViewModel: DataTouristViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prefrencesHelper= PrefrencesHelper(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        authViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AuthViewModel::class.java)
//        authViewModel.setStatusMessage("admin@admin","admin")
        binding.btnLogin.setOnClickListener{
            binding.btnLogin.startAnimation()
            val email=binding.edtEmail.text.toString()
            val password=binding.edtPassword.text.toString()

            if (email.isEmpty() && password.isEmpty())
            {
                binding.edtEmail.error=getString(R.string.email_must)
                binding.edtPassword.error=getString(R.string.password_must)
                messageStatus(getString(R.string.email_password_must))
                binding.btnLogin.revertAnimation()
            }
            else if (email.isEmpty())
            {
                binding.edtEmail.error=getString(R.string.email_must)
                messageStatus(getString(R.string.email_must))
                binding.btnLogin.revertAnimation()
            }
            else if (password.isEmpty())
            {
                binding.edtPassword.error=getString(R.string.password_must)
                messageStatus(getString(R.string.password_must))
                binding.btnLogin.revertAnimation()
            }
            else if (email.isNotEmpty() && password.isNotEmpty())
            {
                authViewModel.setStatusMessage(email,password)
//                authViewModel.setStatusMessage("admin@admin","admin")
                authViewModel.getStatusMessage().observe(this,{dataItem->
                    Log.d("dataItem",dataItem[0].status.toString())
                    if (dataItem[0].status){
                        authViewModel.setLogin(email,password)
                        authViewModel.getLogin().observe(this,{dataUser->
                            val view = this.currentFocus
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                            saveSession(
                                dataUser[0].email.toString(),
                                dataUser[0].password.toString(),
                                dataUser[0].idUser.toString())
                        })
                       dialog(
                            "Success",
                            getString(R.string.login),
                            getString(R.string.berhasil)
                        )
                        moveIntent()
                    }else
                    if (!dataItem[0].status){
                        if (dataItem[0].message=="Password failed"){
                            messageStatus(getString(R.string.wrong__password))
                        }
                        else if (dataItem[0].message=="User not found"){
                            messageStatus(getString(R.string.email_not_found))
                        }
                        binding.btnLogin.revertAnimation()
                    }
                })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (prefrencesHelper.getBoolean(Constant.PREFS_IS_LOGIN)){
            moveIntent()
        }
    }
    private  fun moveIntent(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    private fun dialog(
        type:String,
        title:String,
        message:String
    ){
        if(type=="Success"){
            AestheticDialog.Builder(this@LoginActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setDarkMode(false)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(object : OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()

                        finish()
                        //actions...
                    }
                })
                .show()
        }else{
            AestheticDialog.Builder(this@LoginActivity, DialogStyle.FLAT, DialogType.ERROR)
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
    private fun messageStatus(message:String){
        binding.status.text=message
        binding.status.visibility= View.VISIBLE
    }
    private  fun saveSession(email:String, password:String, idUser:String){
        prefrencesHelper.put(Constant.PREFS_IS_EMAIL,email)
        prefrencesHelper.put(Constant.PREFS_IS_PASSWORD,password)
        prefrencesHelper.put(Constant.PREFS_IS_ID_USER,idUser)
        prefrencesHelper.put(Constant.PREFS_IS_LOGIN,true)
    }
}