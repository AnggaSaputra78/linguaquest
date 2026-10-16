package com.linguaquest

import android.app.Application
import com.linguaquest.di.AppContainer

class LinguaQuestApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}