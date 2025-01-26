package com.tugas.klinikhewan

import android.app.Application
import com.tugas.klinikhewan.di.AppContainer
import com.tugas.klinikhewan.di.KlinikHewanContainer

class KlinikHewanApplication: Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = KlinikHewanContainer()
    }
}