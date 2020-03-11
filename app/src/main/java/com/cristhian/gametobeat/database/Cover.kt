package com.cristhian.gametobeat.database

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cover (
        @SerializedName("id") val id: Long,
        @SerializedName("url") val url: String,
        @SerializedName("image_id") val imageId: String) : Parcelable