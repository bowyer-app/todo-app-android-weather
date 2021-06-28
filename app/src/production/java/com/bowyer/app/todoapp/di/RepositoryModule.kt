package com.bowyer.app.todoapp.di

import com.bowyer.app.todoapp.data.datasource.LicenseDataSource
import com.bowyer.app.todoapp.data.datasource.SettingDataSource
import com.bowyer.app.todoapp.data.datasource.ToDoDataSource
import com.bowyer.app.todoapp.data.datasource.WeatherDataSource
import com.bowyer.app.todoapp.data.repository.LicenseRepository
import com.bowyer.app.todoapp.data.repository.SettingRepository
import com.bowyer.app.todoapp.data.repository.ToDoRepository
import com.bowyer.app.todoapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {
  @Provides
  fun provideToDoRepository(dataSource: ToDoDataSource): ToDoRepository {
    return dataSource
  }

  @Provides
  fun provideSettingRepository(dataSource: SettingDataSource): SettingRepository {
    return dataSource
  }

  @Provides
  fun provideWeatherRepository(dataSource: WeatherDataSource): WeatherRepository {
    return dataSource
  }

  @Provides
  fun provideLicenseRepository(dataSource: LicenseDataSource): LicenseRepository {
    return dataSource
  }
}
