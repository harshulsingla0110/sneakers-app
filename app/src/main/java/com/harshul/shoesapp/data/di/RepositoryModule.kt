package com.harshul.shoesapp.data.di

import com.harshul.shoesapp.data.db.ShoeDao
import com.harshul.shoesapp.data.repos.MainRepository
import com.harshul.shoesapp.data.repos.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

}