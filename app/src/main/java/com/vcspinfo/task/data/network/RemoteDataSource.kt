package com.vcspinfo.task.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource {

    companion object {
        private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }

    fun <Api> buildApi(
        api: Class<Api>,

    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
           /* .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Authorization", "Bearer $authToken")

                            when{
                                chain.proceed(it.build()).code == 401 ->{
                                    it.addHeader("Authorization", "Bearer $authRefreshToken")
                                }
                            }

                        }.build())


                    }.also { client ->
                        *//* if (BuildConfig.DEBUG) {
                            val logging = HttpLoggingInterceptor()
                            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                            client.addInterceptor(logging)
                        }*//*

                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }.build()
            )*/
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }


}