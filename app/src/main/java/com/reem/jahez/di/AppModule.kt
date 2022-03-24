package com.reem.jahez.di

import com.reem.jahez.data.AuthDataSource
import com.reem.jahez.data.repository.AuthRepositoryImpl
import com.reem.jahez.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRegistrationDataSource ( ): AuthDataSource = AuthDataSource()


    @Singleton
    @Provides
    fun provideAuthRepo (authDataSource: AuthDataSource ) : AuthRepository = AuthRepositoryImpl(authDataSource)

}