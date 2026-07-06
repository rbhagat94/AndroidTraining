package com.rohanbhagat.androidtraining.network

import com.rohanbhagat.androidtraining.model.Product
import com.rohanbhagat.androidtraining.model.ProductListingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("select") select: String
    ): ProductListingResponse

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") productId: Int
    ): Product
}