package com.example.gadgetlist

import android.app.Application
import android.util.Log
import com.example.gadgetlist.data.AppContainer
import com.example.gadgetlist.data.AppDataContainer

class GadgetListApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
