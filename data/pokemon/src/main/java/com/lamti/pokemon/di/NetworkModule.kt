package com.lamti.pokemon.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lamti.pokemon.network.api.RetrofitPokemonNetworkApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://pokeapi.co/api/v2/"

val networkModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }
}

private fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        )
        .build()
}

private val json = Json { ignoreUnknownKeys = true }

@OptIn(ExperimentalSerializationApi::class)
fun provideConverterFactory(): Converter.Factory = json.asConverterFactory("application/json".toMediaType())


private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
}

private fun provideService(retrofit: Retrofit): RetrofitPokemonNetworkApi {
    return retrofit.create(RetrofitPokemonNetworkApi::class.java)
}
