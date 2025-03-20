package com.cristhian.gametobeat.network;

import com.cristhian.gametobeat.BuildConfig;
import com.cristhian.gametobeat.util.IgdbUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit mRetrofit;

    public static Retrofit getInstance() {
        if (mRetrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request original = chain.request();

                    Request request = original
                            .newBuilder()
                            .addHeader("Authorization", "Bearer "+BuildConfig.IgdbAuthorization)
                            .addHeader("Client-ID", BuildConfig.IgdbKey)
                            .build();

                    return chain.proceed(request);
                }
            }).build();

            mRetrofit = new Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(IgdbUtil.BASE_URL)
                    .build();
        }

        return mRetrofit;
    }


}
