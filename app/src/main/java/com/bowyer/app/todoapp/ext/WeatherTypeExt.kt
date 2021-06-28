package com.bowyer.app.todoapp.ext

import androidx.annotation.DrawableRes
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.domain.WeatherType

@DrawableRes
fun WeatherType.toIconRes(): Int {
  return when (this) {
    WeatherType.CLEAR_SKY -> {
      R.drawable.ic_vector_clear_sky
    }
    WeatherType.FEW_CLOUDS -> {
      R.drawable.ic_vector_few_clouds
    }
    WeatherType.SCATTERED_CLOUDS -> {
      R.drawable.ic_vector_scattered_clouds
    }
    WeatherType.BROKEN_CLOUDS -> {
      R.drawable.ic_vector_broken_clouds
    }
    WeatherType.SHOWER_RAIN -> {
      R.drawable.ic_vector_shower_rain
    }
    WeatherType.RAIN -> {
      R.drawable.ic_vector_rain
    }
    WeatherType.THUNDERSTORM -> {
      R.drawable.ic_vector_thunderstorm
    }
    WeatherType.SNOW -> {
      R.drawable.ic_vector_snow
    }
    WeatherType.MIST -> {
      R.drawable.ic_vector_mist
    }
  }
}

/**
 * 天気タイプ
 * https://openweathermap.org/weather-conditions
 *
 * 画像SVG取得先
 * https://iconstore.co/icons/weather-vector-icons/
 */
fun String.toWeatherType(): WeatherType {
  return when (this) {
    "01d" -> {
      WeatherType.CLEAR_SKY
    }
    "02d" -> {
      WeatherType.FEW_CLOUDS
    }
    "03d" -> {
      WeatherType.SCATTERED_CLOUDS
    }
    "04d" -> {
      WeatherType.BROKEN_CLOUDS
    }
    "09d" -> {
      WeatherType.SHOWER_RAIN
    }
    "10d" -> {
      WeatherType.RAIN
    }
    "11d" -> {
      WeatherType.THUNDERSTORM
    }
    "13d" -> {
      WeatherType.SNOW
    }
    "50d" -> {
      WeatherType.MIST
    }
    else -> {
      WeatherType.CLEAR_SKY
    }
  }
}
