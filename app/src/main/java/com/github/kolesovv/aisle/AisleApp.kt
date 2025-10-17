package com.github.kolesovv.aisle

import android.app.Application
import com.github.kolesovv.aisle.di.DaggerAppComponent
import kotlin.getValue

class AisleApp : Application() {

    val component by lazy {
        DaggerAppComponent.builder().setContext(this).build()
    }
}