package com.bowyer.app.todoapp.data.remote

import com.bowyer.app.todoapp.data.remote.model.WeeklyWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {
  @GET("/data/2.5/onecall")
  suspend fun getDaily(
    @Query("lat") lat: Float,
    @Query("lon") lon: Float,
    @Query("appid") appId: String,
    @Query("lang") lang: String,
    @Query("units") units: String,
    @Query("exclude") exclude: String
  ): WeeklyWeatherResponse
}
