package com.example.rickandmorty.data.DI

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun getRetrofit(): Retrofit {
        val gson = GsonBuilder().create()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val gsonFactory = GsonConverterFactory.create(gson)

        return Retrofit.Builder()
            .baseUrl(DOCKER_URL)
            .client(client)
            .addConverterFactory(gsonFactory)
            .build()
    }

//        @Provides
//        fun getFoodApi(retrofit: Retrofit): FoodAPI {
//            return retrofit.create(FoodAPI::class.java)
//        }

    companion object {
        private const val DOCKER_URL = "http://localhost:8080/"
    }

}
