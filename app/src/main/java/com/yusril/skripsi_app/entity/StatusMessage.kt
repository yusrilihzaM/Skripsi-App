package com.yusril.skripsi_app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusMessage(
    var status:Boolean,
    var message:String
): Parcelable