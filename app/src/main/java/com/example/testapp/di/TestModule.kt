package com.example.testapp.di
import android.content.Context
import androidx.room.Room
import com.example.testapp.model.AndroidLogger
import com.example.testapp.model.Logger
import com.example.testapp.model.UserService
import com.example.testapp.room.AppDatabase
import com.example.testapp.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "database.db",
        ).createFromAsset("init_db_testapp.db").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.getUserDao()
    }

    @Provides
    @Singleton
    fun provideUserService(
        userDao: UserDao,
        logger: Logger,
    ): UserService {
        return UserService(userDao, logger)
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger = AndroidLogger()
}
