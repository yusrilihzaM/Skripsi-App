package com.yusril.skripsi_app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class MenuForecast(
    var title:String,
    var subTitle:String,
    var ic: Int
): Parcelable
