package com.sihabudin.itask

import android.app.Application
import com.sihabudin.itask.core.di.databaseModule
import com.sihabudin.itask.core.di.repositoryModule
import com.sihabudin.itask.di.useCaseModule
import com.sihabudin.itask.di.viewModelModule
import com.sihabudin.itask.di.workModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory

import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            workManagerFactory()
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    workModule
                )
            )
        }
    }
}