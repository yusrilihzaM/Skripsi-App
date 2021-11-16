package com.yusril.skripsi_app.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetTouristDataTypeResponse(
	val dataTourist: List<DataTouristItem?>? = null,
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataTouristItem(
	val no: String? = "",
	val touristDataType: String? = "",
	val idTouristDataType: String? = "",
	val dataType: String? = ""
) : Parcelable
