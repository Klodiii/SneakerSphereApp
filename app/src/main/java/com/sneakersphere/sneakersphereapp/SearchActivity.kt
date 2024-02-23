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

    private val productList = generateProductData()

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
    }

    private fun generateProductData(): List<Product> {
        // Dummy data for the search results
        return listOf(
            Product("Air Jordan 1 Mid", "PHP8,395", R.drawable.shoe1,""),
            Product("Jordan Max Aura 5", "PHP5,920", R.drawable.shoe2,""),
            Product("Nike Blazer Mid '77", "PHP5,095", R.drawable.shoe3,""),
            Product("Tatum 1", "PHP6,895", R.drawable.shoe4,""),
            Product("Nike Blazer Mid '77 Vintage", "PHP4,990", R.drawable.shoe7,""),
            Product("Nike Air Force 1 '07", "PHP5,495", R.drawable.shoe5w,"")
        )
    }
}
