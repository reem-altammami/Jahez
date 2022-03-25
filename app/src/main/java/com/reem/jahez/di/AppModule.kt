package com.reem.jahez.di

import com.reem.jahez.data.AuthDataSource
import com.reem.jahez.data.RestaurantsApi
import com.reem.jahez.data.repository.AuthRepositoryImpl
import com.reem.jahez.data.repository.RestaurantsRepositoryImpl
import com.reem.jahez.domain.repository.AuthRepository
import com.reem.jahez.domain.repository.RestaurantsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


private const val BASE_URL ="https://jahez-other-oniiphi8.s3.eu-central-1.amazonaws.com/"
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRegistrationDataSource ( ): AuthDataSource = AuthDataSource()


    @Singleton
    @Provides
    fun provideAuthRepo (authDataSource: AuthDataSource ) : AuthRepository = AuthRepositoryImpl(authDataSource)

    @Singleton
    @Provides
    fun provideMoshi():Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideRestaurantApi(moshi: Moshi): RestaurantsApi{
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
            BASE_URL).build().create(RestaurantsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRestaurantsRepo(restaurantsApi: RestaurantsApi):RestaurantsRepository{
      return RestaurantsRepositoryImpl(restaurantsApi)
    }

}