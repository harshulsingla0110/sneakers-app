package com.harshul.shoesapp.data.di

import android.content.Context
import androidx.room.Room
import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.db.ShoesDatabase
import com.harshul.shoesapp.data.repos.MainRepository
import com.harshul.shoesapp.data.repos.MainRepositoryImpl
import com.harshul.shoesapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ShoesDatabase::class.java,
        Constants.SHOES_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: ShoesDatabase) = db.getShoesDao()

    @Singleton
    @Provides
    fun provideRepository(
        shoeDao: ShoeDao
    ): MainRepository = MainRepositoryImpl(shoeDao)
}