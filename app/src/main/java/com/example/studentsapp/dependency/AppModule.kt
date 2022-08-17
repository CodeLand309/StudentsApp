package com.example.studentsapp.dependency

import com.example.studentsapp.data.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    enum class UserType{
        Student,
        Teacher,
        Unknown
    }

    //Need to Change this
    private const val BASE_URL = "http://localhost:5000/"

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesRestApiInterface(retrofit: Retrofit) : ApiInterface = retrofit.create(ApiInterface::class.java)
}