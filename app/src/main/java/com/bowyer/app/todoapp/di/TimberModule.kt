package com.bowyer.app.todoapp.di

import com.bowyer.app.todoapp.BuildConfig
import com.bowyer.app.todoapp.util.CrashReportingTree
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimberModule {
  @Singleton
  @Provides
  fun provideTimberTree(): Timber.Tree {
    return if (BuildConfig.DEBUG) {
      Timber.DebugTree()
    } else {
      CrashReportingTree()
    }
  }
}
