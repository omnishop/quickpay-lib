package com.bda.quickpay_lib.api


import com.bda.quickpay_lib.utils.QuickPayUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class APIGenerator(baseUrl: String) {
    private var logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
    private var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    private var builder: Retrofit.Builder? = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.addNetworkInterceptor { chain ->
            var request = chain.request()
            var httpUrl = request.url()
            val builder = request.newBuilder().url(httpUrl)
            builder.addHeader("x-api-key", QuickPayUtils.getXApiKey())
            builder.addHeader("ott", "FPTOTT")
            builder.addHeader("Content-Type", "application/json")
            request = builder.build()
            chain.proceed(request)
        }.interceptors().add(logging)
        builder!!.client(httpClient.build())
        val retrofit = builder!!.build()
        return retrofit.create(serviceClass)
    }

}
