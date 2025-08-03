package com.example.rickandmorty.data.DI

import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.data.api.params.StatusAdapter
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {

        val gson = GsonBuilder()
            .registerTypeAdapter(CharacterStatus::class.java, StatusAdapter())
            .create()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val gsonFactory = GsonConverterFactory.create(gson)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonFactory)
            .build()
    }

    @Provides
    @Singleton
    fun RickAndMortyAPI(retrofit: Retrofit): RickAndMortyAPI {
        return retrofit.create(RickAndMortyAPI::class.java)
    }

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

}
