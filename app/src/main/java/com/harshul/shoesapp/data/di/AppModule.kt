package com.harshul.shoesapp.data.di

import android.content.Context
import androidx.room.Room
import com.harshul.shoesapp.data.db.ShoesDatabase
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
    fun provideShoeDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ShoesDatabase::class.java,
        Constants.SHOES_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideShoeDao(db: ShoesDatabase) = db.getShoesDao()

}