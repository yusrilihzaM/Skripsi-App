package com.yusril.skripsi_app.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataManualResponse(

	@field:SerializedName("data_manual")
	val dataManual: List<DataManualItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataManualItem(
	val no: String? = "",
	@field:SerializedName("mad")
	val mad: String? = null,

	@field:SerializedName("t")
	val T: String? = null,

	@field:SerializedName("unadjusted")
	val unadjusted: String? = null,

	@field:SerializedName("data_pengunjung")
	val dataPengunjung: String? = null,

	@field:SerializedName("smoothed")
	val smoothed: String? = null,

	@field:SerializedName("adjusted")
	val adjusted: String? = null,

	@field:SerializedName("seasonal_index")
	val seasonalIndex: String? = null,

	@field:SerializedName("mape")
	val mape: String? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("ctdma")
	val ctdma: String? = null,

	@field:SerializedName("ratio")
	val ratio: String? = null
) : Parcelable
