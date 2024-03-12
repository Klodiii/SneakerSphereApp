package com.sneakersphere.sneakersphereapp

import java.io.Serializable


data class CartItem(
    val name: String,
    val price: Double,
    val size: String,
    val imageResource: Int) :
    Serializable

