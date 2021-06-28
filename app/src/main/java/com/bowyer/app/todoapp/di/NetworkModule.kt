package com.bowyer.app.todoapp.di

import com.bowyer.app.todoapp.Const
import com.bowyer.app.todoapp.data.remote.OpenWeatherMapApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(10L, TimeUnit.SECONDS)
      .writeTimeout(10L, TimeUnit.SECONDS)
      .readTimeout(10L, TimeUnit.SECONDS)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(
    okHttpClient: OkHttpClient
  ): Retrofit {
    val httpClient = okHttpClient.newBuilder()
      .build()

    return Retrofit.Builder().baseUrl(Const.WEATHER_API + "/")
      .client(httpClient)
      .addConverterFactory(
        MoshiConverterFactory.create(
          Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
        )
      )
      .build()
  }

  @Provides
  fun provideWeatherApi(retrofit: Retrofit): OpenWeatherMapApi {
    return retrofit.create(OpenWeatherMapApi::class.java)
  }
}
