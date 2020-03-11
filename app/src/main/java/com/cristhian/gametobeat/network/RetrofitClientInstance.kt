package com.cristhian.gametobeat.network

import com.cristhian.gametobeat.BuildConfig
import com.cristhian.gametobeat.util.IgdbUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {

    private const val BASE_URL = "https://api-v3.igdb.com/"
    const val BASE_IMAGE_URL = "https://images.igdb.com/igdb/image/upload/t_"
    const val IMAGE_URL_COVER_BIG = "cover_big"

    private val okHttpClient =
            OkHttpClient.Builder().addInterceptor { chain ->
                val original = chain.request()
                val request = original
                        .newBuilder()
                        .addHeader(
                                "user-key",
                                BuildConfig.IgdbKey
                        )
                        .build()
                chain.proceed(request)
            }.build()

    val igdbApiService: IgdbApiService by lazy {
        return@lazy Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build()
                .create(IgdbApiService::class.java)
    }
}