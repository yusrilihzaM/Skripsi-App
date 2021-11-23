package com.yusril.skripsi_app.ui.main

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yusril.skripsi_app.R
import com.yusril.skripsi_app.databinding.ActivityMainBinding
import com.yusril.skripsi_app.ui.home.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentManager = supportFragmentManager
        val homeFragment =HomeFragment()
        fragmentManager
            .beginTransaction()
            .add(R.id.container, homeFragment)
            .commit()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        binding.bottomNavigation.setItemSelected(R.id.home,true)
        binding.bottomNavigation.setOnItemSelectedListener { id->
            when(id){
                R.id.home ->{
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, homeFragment)
                        .commit()
                }

                R.id.akun->{
//                    val adminAkunFragment = AdminAkunFragment()
//                    fragmentManager
//                        .beginTransaction()
//                        .replace(R.id.container, adminAkunFragment)
//                        .commit()
                }
            }

        }
    }
}