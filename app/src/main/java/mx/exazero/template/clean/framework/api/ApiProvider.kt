package mx.exazero.template.clean.framework.api

import com.google.gson.GsonBuilder
import mx.exazero.template.clean.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  Created by JAzcorra96 on 10/11/2020
 */

class ApiProvider(authorizationInterceptor: AuthorizationInterceptor) {
    private var retrofit: Retrofit

    companion object {
        const val baseUrl = BuildConfig.API_BASE_URL
    }

    init {
        val httpClientBuilder = OkHttpClient.Builder()
        if(BuildConfig.DEBUG){
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            httpClientBuilder.addInterceptor(logging)
        }

        httpClientBuilder.apply {
            addInterceptor(authorizationInterceptor)
            cache(null)
        }

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClientBuilder.build())
            .build()

    }

    fun <S> getEndpoint(serviceClass: Class<S>): S = retrofit.create(serviceClass)

}