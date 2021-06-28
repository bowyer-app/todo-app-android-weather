package com.bowyer.app.todoapp.di

import android.content.Context
import androidx.room.Room
import com.bowyer.app.todoapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
  @Singleton
  @Provides
  fun provideRoomDB(@ApplicationContext context: Context): AppDatabase {
    return Room.databaseBuilder(
      context,
      AppDatabase::class.java,
      "todo.db"
    ).build()
  }

  @Singleton
  @Provides
  internal fun provideToDoDao(db: AppDatabase) = db.todoDao()
}
