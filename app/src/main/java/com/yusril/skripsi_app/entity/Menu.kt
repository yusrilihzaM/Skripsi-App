package com.yusril.skripsi_app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var title:String,
    var ic: Int
): Parcelable
