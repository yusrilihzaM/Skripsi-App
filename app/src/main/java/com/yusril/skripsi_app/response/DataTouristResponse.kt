package com.yusril.skripsi_app.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTouristResponse(

	@field:SerializedName("data_tourist")
	val dataTouristType: List<DataTouristTypeItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataTouristItem(
	val no: String? = "",
	@field:SerializedName("month")
	val month: String? = null,

	@field:SerializedName("t")
	val T: String? = null,

	@field:SerializedName("id_data_pengunjung")
	val idDataPengunjung: String? = null,

	@field:SerializedName("year")
	val year: String? = null,

	@field:SerializedName("data_pengunjung")
	val dataPengunjung: String? = null,

	@field:SerializedName("id_tourist_data_type")
	val idTouristDataType: String? = null
) : Parcelable
