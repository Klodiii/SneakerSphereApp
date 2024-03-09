package com.sneakersphere.sneakersphereapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {
    @GET("public/api")
    fun getShoeItem(): Call<ProductResponse>

    @GET("public/api/{id}")
    fun getProduct(@Path("id") productId: String): Call<Product>
}

