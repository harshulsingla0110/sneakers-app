package com.harshul.shoesapp.data.di

import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.repos.MainRepository
import com.harshul.shoesapp.data.repos.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        shoeDao: ShoeDao
    ): MainRepository = MainRepositoryImpl(shoeDao)

}