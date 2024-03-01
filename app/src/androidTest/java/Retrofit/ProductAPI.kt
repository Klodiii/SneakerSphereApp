package Retrofit

import com.sneakersphere.sneakersphereapp.Product
import retrofit2.http.GET

interface ProductAPI {
    @GET("posts/1")
    suspend fun getProduct(): Response<Product>
}