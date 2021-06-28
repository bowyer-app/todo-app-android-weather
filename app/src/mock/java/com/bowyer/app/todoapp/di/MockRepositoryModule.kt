package com.bowyer.app.todoapp.di

import com.bowyer.app.todoapp.data.datasource.LicenseDataSource
import com.bowyer.app.todoapp.data.datasource.MockSettingDataSource
import com.bowyer.app.todoapp.data.datasource.MockWeatherDataSource
import com.bowyer.app.todoapp.data.repository.LicenseRepository
import com.bowyer.app.todoapp.data.datasource.ToDoDataSource
import com.bowyer.app.todoapp.data.repository.SettingRepository
import com.bowyer.app.todoapp.data.repository.ToDoRepository
import com.bowyer.app.todoapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MockRepositoryModule {
  @Provides
  fun provideToDoRepository(dataSource: ToDoDataSource): ToDoRepository {
    return dataSource
  }

  @Provides
  fun provideSettingRepository(dataSource: MockSettingDataSource): SettingRepository {
    return dataSource
  }

  @Provides
  fun provideWeatherRepository(dataSource: MockWeatherDataSource): WeatherRepository {
    return dataSource
  }

  @Provides
  fun provideLicenseRepository(dataSource: LicenseDataSource): LicenseRepository {
    return dataSource
  }
}
