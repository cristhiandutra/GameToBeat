package com.cristhian.gametobeat.database

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "game")
data class Game (
        @PrimaryKey
        @SerializedName("id") val gameId: Long,
        val name: String,
        val summary: String,
        val rating: Double,
        var status: Int,
        @Embedded val cover: Cover
) : Parcelable {
    companion object {
        const val STATUS_NULL = 0
        const val STATUS_BEAT = 1
        const val STATUS_PLAYING = 2
        const val STATUS_WANT = 3
    }
}