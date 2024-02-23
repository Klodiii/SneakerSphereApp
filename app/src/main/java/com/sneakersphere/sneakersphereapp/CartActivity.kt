package com.sneakersphere.sneakersphereapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalAmountTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val backButton: View = findViewById(R.id.backButtonCart)
        backButton.setOnClickListener {
            finish()
        }

        // Initialize RecyclerView and CartAdapter
        recyclerView = findViewById(R.id.cartRecyclerView)
        cartAdapter = CartAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        // Fetch cart items from local storage or any temporary storage
        val cartItems = ShoppingCart.getItems()
        cartAdapter.submitList(cartItems)

        // Set up total amount TextView
        totalAmountTextView = findViewById(R.id.totalAmountTextView)
        updateTotalAmount()

        // Set text color for checkout button
        val checkoutButton: Button = findViewById(R.id.checkoutButton)
        checkoutButton.setTextColor(ContextCompat.getColor(this, android.R.color.white))
    }

    fun updateTotalAmount() {
        val totalPrice = ShoppingCart.getTotalPrice()
        totalAmountTextView.text = String.format("PHP%.2f", totalPrice)
    }

    private fun addToCart(productName: String, productPrice: String, selectedSize: String, productImage: Int) {
        val priceNumeric = parsePrice(productPrice)
        val cartItem = CartItem(productName, priceNumeric, selectedSize ?: "", productImage)
        ShoppingCart.addItem(cartItem)
        // Update the UI to reflect the change in total amount
        updateTotalAmount()
    }

    private fun parsePrice(price: String): Double {
        // Remove the currency symbol and commas, then parse the string to a double
        return price.replace("PHP", "").replace(",", "").toDoubleOrNull() ?: 0.0
    }
    fun openCheckout(view: View) {
        val intent = Intent(this, CheckoutActivity::class.java).apply {
            putExtra("cartItems", ShoppingCart.getItems().toTypedArray())
        }
        startActivity(intent)
    }

}
