package com.sneakersphere.sneakersphereapp


data class Product(
    val id: String,
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val created_at: String,
    val updated_at: String,
    val sizes: List<String>,
    val relatedProductIds: List<String>
)


