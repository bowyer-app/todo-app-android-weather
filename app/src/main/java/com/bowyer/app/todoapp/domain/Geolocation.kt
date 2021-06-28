package com.bowyer.app.todoapp.domain

/**
 * 現在地情報
 */
data class Geolocation(
  val latitude: Latitude,
  val longitude: Longitude,
  val localeName: String
)
