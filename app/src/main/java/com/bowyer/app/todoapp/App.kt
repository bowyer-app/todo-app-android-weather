package com.bowyer.app.todoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: App
    }

    @Inject
    lateinit var tree: Timber.Tree

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(tree)
    }
}
