package com.arjungupta08.arjunassignment.api

import com.arjungupta08.arjunassignment.model.AllImagesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("media-coverages")
    fun getImages(
        @Query("limit") limit : Int
    ) : Call<List<AllImagesItem>>

}