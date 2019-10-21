package com.rubenquadros.apitest.di.module

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rubenquadros.apitest.data.api.ApiService
import com.rubenquadros.apitest.utils.ApplicationConstants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule(private val baseUrl: String, private val application: Application) {

    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun provideCache(): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    fun provideHttpClient(cache: Cache): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.connectTimeout(ApplicationConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        httpClient.readTimeout(ApplicationConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        httpClient.writeTimeout(ApplicationConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}