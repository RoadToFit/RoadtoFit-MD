package com.example.roadtofit.data.retrofit

import com.example.roadtofit.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig2 {

    companion object {
        private const val BASE_URL = "https://roadtofit-ml-aw4sah573q-uc.a.run.app/"

        // Function to get ApiService without token
        fun getApiService(): ApiService2 {
            return createApiService(null)
        }

        // Function to get ApiService with token
        fun getApiServiceWithToken(token: String): ApiService2 {
            return createApiService(token)
        }

        private fun createApiService(token: String?): ApiService2 {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)

            if (token != null) {
                val tokenInterceptor = Interceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
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

            return retrofit.create(ApiService2::class.java)
        }
    }
}
