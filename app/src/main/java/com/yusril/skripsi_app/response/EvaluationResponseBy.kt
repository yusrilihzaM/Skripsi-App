package com.yusril.skripsi_app.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EvaluationResponseBy(

	@field:SerializedName("data_evaluation")
	val dataEvaluation: DataEvaluationBy? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataEvaluationBy(

	@field:SerializedName("mad")
	val mad: String? = null,

	@field:SerializedName("id_error_measurement")
	val idErrorMeasurement: String? = null,

	@field:SerializedName("rsfe")
	val rsfe: String? = null,

	@field:SerializedName("id_method_type")
	val idMethodType: String? = null,

	@field:SerializedName("id_tourist_data_type")
	val idTouristDataType: String? = null,

	@field:SerializedName("mape")
	val mape: String? = null,

	@field:SerializedName("ts")
	val ts: String? = null
) : Parcelable
