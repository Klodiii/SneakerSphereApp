package com.sneakersphere.sneakersphereapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {
    @GET("products/{productId}")
    fun getProduct(@Path("productId") productId: String): Call<Product>

    @GET("products")
    fun getProducts(): Call<List<Product>>
}
