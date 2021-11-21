package com.yusril.skripsi_app.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FutureByDataTypeResponse(

	@field:SerializedName("data_future")
	val dataFuture: List<DataFutureByTypeMethodItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataFutureByTypeMethodItem(
	val no: String? = "",
	@field:SerializedName("t_future")
	val tFuture: String? = null,

	@field:SerializedName("season_future")
	val seasonFuture: String? = null,

	@field:SerializedName("id_method_type")
	val idMethodType: String? = null,

	@field:SerializedName("adjusted_forecast")
	val adjustedForecast: String? = null,

	@field:SerializedName("id_tourist_data_type")
	val idTouristDataType: String? = null,

	@field:SerializedName("id_seasonal_index")
	val idSeasonalIndex: String? = null,

	@field:SerializedName("unadjusted_forecast")
	val unadjustedForecast: String? = null,

	@field:SerializedName("id_forecast_future")
	val idForecastFuture: String? = null,

	@field:SerializedName("year_future")
	val yearFuture: String? = null
) : Parcelable
