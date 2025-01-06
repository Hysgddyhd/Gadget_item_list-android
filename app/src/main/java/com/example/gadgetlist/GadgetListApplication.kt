package com.example.gadgetlist

import android.app.Application
import android.util.Log
import com.example.gadgetlist.data.AppContainer
import com.example.gadgetlist.data.AppDataContainer
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth

class GadgetListApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        val firebaseProject = FirebaseApp.initializeApp(this)
        val auth = FirebaseAuth.getInstance()
        container = AppDataContainer(this,auth)




    }
}
