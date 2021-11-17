package com.yusril.skripsi_app.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetTouristDataTypeResponse(
	val dataTouristType: List<DataTouristTypeItem?>? = null,
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataTouristTypeItem(
	val no: String? = "",
	val touristDataType: String? = "",
	val idTouristDataType: String? = "",
	val dataType: String? = ""
) : Parcelable
