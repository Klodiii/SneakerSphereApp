package com.sneakersphere.sneakersphereapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: ImageButton
    private lateinit var recyclerViewResults: RecyclerView
    private lateinit var textViewNoResults: TextView
    private lateinit var productAdapter: ProductAdapter

    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        editTextSearch = findViewById(R.id.editTextSearch)
        buttonSearch = findViewById(R.id.searchButton)
        recyclerViewResults = findViewById(R.id.recyclerViewResults)
        textViewNoResults = findViewById(R.id.textViewNoResults)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        productAdapter = ProductAdapter(productList, object : ProductAdapter.OnProductClickListener {
            override fun onProductClick(product: Product) {
                // Handle product click
            }
        })

        recyclerViewResults.layoutManager = LinearLayoutManager(this)
        recyclerViewResults.adapter = productAdapter

        buttonSearch.setOnClickListener {
            performSearch()
        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun performSearch() {
        val query = editTextSearch.text.toString().trim()

        if (query.isNotEmpty()) {
            val filteredList = productList.filter { it.name.contains(query, ignoreCase = true) }

            if (filteredList.isNotEmpty()) {
                productAdapter.submitList(filteredList)
                recyclerViewResults.visibility = RecyclerView.VISIBLE
                textViewNoResults.visibility = TextView.GONE
            } else {
                productAdapter.submitList(emptyList())
                recyclerViewResults.visibility = RecyclerView.GONE
                textViewNoResults.visibility = TextView.VISIBLE
            }
        } else {
            showError("Please enter a search query")
        }
    }

    private fun showError(message: String) {
        textViewNoResults.text = message
        recyclerViewResults.visibility = RecyclerView.GONE
        textViewNoResults.visibility = TextView.VISIBLE
    }
}
