package com.elmoselhy.elfiltardelivery.data.remote.builder

import com.elmoselhy.elfiltardelivery.BuildConfig
import com.elmoselhy.elfiltardelivery.commons.helpers.MyConstants
import com.elmoselhy.elfiltardelivery.data.remote.apiservice.ApiService
import com.elmoselhy.elfiltardelivery.data.remote.apiservice.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    @Named("logging")
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    @Named("client")
    fun provideOkHttpClient(
        @Named("logging") logging: Interceptor,
        header: HeaderInterceptor,
    ): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(header)
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        } else
            OkHttpClient.Builder()
                .addInterceptor(header)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("client") okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(MyConstants.Public.SERVER_API_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}