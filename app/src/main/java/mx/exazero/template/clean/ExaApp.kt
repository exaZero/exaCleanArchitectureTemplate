package mx.exazero.template.clean

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
@HiltAndroidApp
class ExaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}