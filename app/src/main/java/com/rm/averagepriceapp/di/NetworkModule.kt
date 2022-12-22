package com.rm.averagepriceapp.di

import com.rm.averagepriceapp.common.Constants.BASE_URL
import com.rm.averagepriceapp.data.remote.PropertiesApi
import com.rm.averagepriceapp.data.repository.PropertiesRepositoryImpl
import com.rm.averagepriceapp.domain.repository.PropertiesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(moshi: Moshi): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PropertiesApi {
        return retrofit.create(PropertiesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideCoinRepository(api: PropertiesApi): PropertiesRepository {
        return PropertiesRepositoryImpl(api)
    }
}