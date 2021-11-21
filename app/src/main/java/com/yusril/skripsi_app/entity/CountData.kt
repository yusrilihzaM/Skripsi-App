package com.yusril.skripsi_app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountData(
    var status:Boolean,
    var countData:String
): Parcelable