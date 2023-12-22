package com.example.roadtofit.data.retrofit

import android.util.Log
import com.example.roadtofit.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        private const val BASE_URL = "https://roadtofit-be-aw4sah573q-uc.a.run.app"


        fun getApiService(): ApiService {
            return createApiService(null)
        }

        private fun createApiService(token: String?): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)

            if (token != null) {
                Log.d("TokenInterceptor", "Adding token: $token to the request")
                val tokenInterceptor = Interceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build()

                    chain.proceed(newRequest)
                }
                clientBuilder.addInterceptor(tokenInterceptor)
            }

            val client = clientBuilder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
