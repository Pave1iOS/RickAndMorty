package com.example.rickandmorty.data.DI

import android.app.Application
import androidx.room.Room
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.data.database.AppDatabase
import com.example.rickandmorty.data.database.RickAndMortyDao
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {

        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "rick_and_morty_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: AppDatabase): RickAndMortyDao {
        return database.rickAndMortyDao()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyAPI(retrofit: Retrofit): RickAndMortyAPI {
        return retrofit.create(RickAndMortyAPI::class.java)
    }

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

}
