package com.sneakersphere.sneakersphereapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CheckoutActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalItemsTextView: TextView
    private lateinit var totalPriceTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val backButton: ImageView = findViewById(R.id.backButtonCart)
        backButton.setOnClickListener {
            finish()
        }

        val checkoutRecyclerView: RecyclerView = findViewById(R.id.cartRecyclerView)
        cartAdapter = CartAdapter()
        checkoutRecyclerView.adapter = cartAdapter
        checkoutRecyclerView.layoutManager = LinearLayoutManager(this)

        totalItemsTextView = findViewById(R.id.totalItemsTextView)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.text = "Confirm Checkout"
        confirmButton.setOnClickListener {
            confirmCheckout()
        }

        updateCheckout()
    }

    private fun updateCheckout() {
        val cartItems = ShoppingCart.getItems()
        cartAdapter.submitList(cartItems.toMutableList())

        val totalItems = cartItems.size
        totalItemsTextView.text = "Total Items: $totalItems"

        val totalPrice = calculateTotalPrice(cartItems)
        totalPriceTextView.text = "TOTAL: PHP$totalPrice"
    }

    private fun calculateTotalPrice(cartItems: List<CartItem>): Double {
        var totalPrice = 0.0
        for (item in cartItems) {
            totalPrice += item.price
        }
        return totalPrice
    }

    private fun confirmCheckout() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Confirm Checkout")
            .setMessage("Are you sure you want to checkout?")
            .setPositiveButton("Yes") { _, _ ->
                ShoppingCart.clearCart()
                updateCheckout()
            }
            .setNegativeButton("No", null)
            .create()
        alertDialog.show()
    }

}