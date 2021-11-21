package com.sihabudin.itask.core.di

import androidx.room.Room
import com.sihabudin.itask.core.data.TaskRepository
import com.sihabudin.itask.core.data.source.local.LocalDataSource
import com.sihabudin.itask.core.data.source.local.room.TaskDatabase
import com.sihabudin.itask.core.domain.repository.ITaskRepository
import com.sihabudin.itask.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<TaskDatabase>().TaskDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("itasksihab".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            TaskDatabase::class.java, "itask.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}


val repositoryModule = module {
    single { LocalDataSource(get()) }
    factory { AppExecutors() }
    single<ITaskRepository> {
        TaskRepository(
            get(),
            get()
        )
    }
}
