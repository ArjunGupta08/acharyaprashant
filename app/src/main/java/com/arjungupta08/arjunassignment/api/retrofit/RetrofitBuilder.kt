package com.arjungupta08.arjunassignment.retrofit

import com.arjungupta08.arjunassignment.api.ImagesApi
import com.arjungupta08.arjunassignment.utils.Const.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val imagesApi = retrofitBuilder.create(ImagesApi::class.java)

}