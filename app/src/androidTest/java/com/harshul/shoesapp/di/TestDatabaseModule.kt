package com.harshul.shoesapp.di

import android.content.Context
import androidx.room.Room
import com.harshul.shoesapp.data.db.ShoesDatabase
import com.harshul.shoesapp.data.di.AppModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
@Module
class TestDatabaseModule {

    @Singleton
    @Provides
    fun providesTestDb(@ApplicationContext context: Context): ShoesDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            ShoesDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideTestShoeDao(db: ShoesDatabase) = db.getShoesDao()

}