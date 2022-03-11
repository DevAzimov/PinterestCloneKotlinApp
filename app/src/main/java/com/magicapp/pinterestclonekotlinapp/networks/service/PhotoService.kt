package com.magicapp.pinterestclonekotlinapp.networks.service


import com.magicapp.pinterestclonekotlinapp.models.PhotoList
import retrofit2.Call


import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface PhotoService {

    companion object {
        private const val ACCESS_KEY = "5Nf9HdffcCJmmTXQIrkDP6JBZZZyqakIGVzKzWSXr4E"
        const val clientId = "Client-ID"
    }

    @Headers("Authorization:$clientId $ACCESS_KEY")
    @GET("photos")
    fun getPhotos(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<PhotoList>

}