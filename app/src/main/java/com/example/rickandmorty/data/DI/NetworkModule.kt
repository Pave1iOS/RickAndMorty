package com.example.rickandmorty.data.DI

import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.domain.NetworkRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
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

    @Provides
    @Singleton
    fun NetworkRepository(api: RickAndMortyAPI): NetworkRepository {
        return NetworkRepository(api)
    }



    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

}
