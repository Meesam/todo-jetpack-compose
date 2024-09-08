package com.todoApp.todojetpackcompose.di

import com.todoApp.todojetpackcompose.api.ITodoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
      return Retrofit
          .Builder()
          .baseUrl("http://10.0.2.2:5185/api/Todo/")
          .addConverterFactory(GsonConverterFactory.create())
          .build()
  }

    @Singleton
    @Provides
    fun provideTodoApi(retrofit: Retrofit):ITodoApi{
        return retrofit.create(ITodoApi::class.java)
    }

}