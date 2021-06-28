package com.bowyer.app.todoapp.data.datasource

import android.content.Context
import com.bowyer.app.todoapp.data.remote.model.LibrariesResponse
import com.bowyer.app.todoapp.data.repository.LicenseRepository
import com.bowyer.app.todoapp.data.translator.toLibraryContent
import com.bowyer.app.todoapp.domain.LibraryContent
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber
import javax.inject.Inject

class LicenseDataSource @Inject constructor(
  private val context: Context
) : LicenseRepository {
  companion object {
    private const val LICENSE_JSON_PATH = "licenses.json"
  }

  override suspend fun loadLicenses(): List<LibraryContent> {
    try {
      val json =
        context.assets.open(LICENSE_JSON_PATH).reader(charset = Charsets.UTF_8)
          .use { it.readText() }
      val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
      val jsonAdapter = moshi.adapter(LibrariesResponse::class.java)

      val response = jsonAdapter.fromJson(json)
      return response?.libraries?.map {
        it.toLibraryContent()
      } ?: listOf()
    } catch (e: Throwable) {
      Timber.e(e)
      return listOf()
    }
  }
}
