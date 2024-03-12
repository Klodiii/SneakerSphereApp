package com.sneakersphere.sneakersphereapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var productName: String
    private lateinit var productPrice: String
    private lateinit var productDescription: String
    private var productImage: Int = R.drawable.placeholder_image

    // Variable to store the selected size
    private var selectedSizeButton: Button? = null

    // TextView to display cart items with sizes
    private lateinit var cartItemsTextView: TextView

    private var timer: Timer? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // Find the cartItemsTextView
        cartItemsTextView = findViewById(R.id.cartItemsTextView)

        // Assuming you receive product details through Intent extras
        productName = intent.getStringExtra("productName") ?: ""
        productPrice = intent.getStringExtra("productPrice") ?: ""
        productDescription = intent.getStringExtra("productDescription") ?: ""
        productImage = intent.getIntExtra("productImage", R.drawable.placeholder_image)

        // Update UI elements with product details
        val productNameHeader: TextView = findViewById(R.id.productTitleTextView)
        val productNameImage: TextView = findViewById(R.id.productNameBelowImage)
        val productPriceTextView: TextView = findViewById(R.id.productPriceTextView)
        val productDescriptionTextView: TextView = findViewById(R.id.productDescriptionTextView)
        val productImageView: ImageView = findViewById(R.id.productImageView)

        // Set product details to UI elements
        productNameHeader.text = productName  // Update the product name in the header
        productNameImage.text = productName  // Update the product name below the image
        productPriceTextView.text = productPrice
        productDescriptionTextView.text = productDescription
        productImageView.setImageResource(productImage)

        // Set up click listener for the back button
        val backButton: View = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        // Set up size buttons
        val sizesGridLayout: GridLayout = findViewById(R.id.sizesGridLayout)
        for (size in 41..48) {
            val sizeButton = Button(this)
            sizeButton.text = size.toString()
            sizeButton.setBackgroundColor(Color.TRANSPARENT)
            sizeButton.setTextColor(Color.BLACK)
            sizeButton.textSize = 16f // Set your desired text size
            sizeButton.setBackgroundResource(R.drawable.size_button_background) // Set button background
            sizeButton.setOnClickListener {
                handleSizeSelection(sizeButton)
                selectSize(sizeButton)
            }
            sizesGridLayout.addView(sizeButton)
        }

        // Set up click listener for Add to Cart button
        val addToCartButton: Button = findViewById(R.id.addToCartButton)

        addToCartButton.setOnClickListener {
            Log.d("ProductDetailsActivity", "Add to Cart button clicked")
            // Check if a shoe size is selected
            if (selectedSizeButton != null) {
                // Call a function to add the product to the cart
                val selectedSize = selectedSizeButton!!.text.toString()
                addToCart(productName, productPrice, selectedSize)
            } else {
                Toast.makeText(this, "Please select a shoe size", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up related products RecyclerView
        val relatedProductsRecyclerView: RecyclerView = findViewById(R.id.otherProductsRecyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        relatedProductsRecyclerView.layoutManager = layoutManager

        // Fetch related products (dummy data for demonstration)
        val relatedProductsList = getRelatedProducts()

        // Set up RelatedProductAdapter and bind it to the RecyclerView
        val relatedProductAdapter = RelatedProductAdapter(relatedProductsList)
        relatedProductsRecyclerView.adapter = relatedProductAdapter

        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
        val recyclerView = findViewById<RecyclerView>(R.id.otherProductsRecyclerView)

        // Assuming you want to scroll to the RecyclerView
        nestedScrollView.post {
            nestedScrollView.smoothScrollTo(0, recyclerView.top)
        }

        relatedProductAdapter.setOnItemClickListener { position ->
            val product = relatedProductsList[position]

            // Update UI elements with the selected product details
            productName = product.name
            productPrice = product.price
            productDescription = product.description
            productImage = product.imageResource

            productNameHeader.text = productName
            productNameImage.text = productName
            productPriceTextView.text = productPrice
            productDescriptionTextView.text = productDescription
            productImageView.setImageResource(productImage)

            // Start the new activity with the updated product details
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra("productName", product.name)
                putExtra("productPrice", product.price)
                putExtra("productImage", product.imageResource)
                putExtra("productDescription", product.description)
            }
            startActivity(intent)
        }

        // Scroll to a specific position (e.g., first item) automatically
        val firstItemPosition = 0
        layoutManager.smoothScrollToPosition(relatedProductsRecyclerView, RecyclerView.State(), firstItemPosition)

        // Start automatic scrolling of related products
        startAutoScroll(relatedProductsRecyclerView)
    }

    private fun startAutoScroll(recyclerView: RecyclerView) {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                recyclerView.post {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val currentPosition = layoutManager.findFirstVisibleItemPosition()
                    if (currentPosition < recyclerView.adapter!!.itemCount - 1) {
                        recyclerView.smoothScrollToPosition(currentPosition + 1)
                    } else {
                        recyclerView.smoothScrollToPosition(0)
                    }
                }
            }
        }, 3000, 3000) // Auto scroll every 3 seconds
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    private fun handleSizeSelection(selectedButton: Button) {
        // Get the GridLayout from the NestedScrollView
        val sizesGridLayout: GridLayout = findViewById(R.id.sizesGridLayout)

        // Iterate through all size buttons in the GridLayout
        for (i in 0 until sizesGridLayout.childCount) {
            val button = sizesGridLayout.getChildAt(i) as Button

            // Set up OnClickListener for normal click
            button.setOnClickListener {
                // On normal click, update the appearance of the clicked button
                selectSize(button)
                // Set the selected size button
                selectedSizeButton = button
            }
        }
    }

    private fun selectSize(selectedButton: Button) {
        // Deselect previously selected button
        selectedSizeButton?.let {
            it.setBackgroundResource(R.drawable.size_button_background)
            it.setTextColor(Color.BLACK)
        }

        // Select the clicked button
        selectedButton.setBackgroundResource(R.drawable.selected_size_button_background)
        selectedButton.setTextColor(Color.WHITE)
        selectedSizeButton = selectedButton

        // Log the selected size button
        Log.d("ProductDetailsActivity", "Selected Size: ${selectedSizeButton?.text}")
    }

    private fun addToCart(productName: String, productPrice: String, selectedSize: String) {
        // Convert the price to a Double
        val priceNumeric = parsePrice(productPrice)

        // Create a new CartItem object with the converted price
        val cartItem = CartItem(productName, priceNumeric, selectedSize ?: "", productImage)

        // Add the item to the shopping cart
        ShoppingCart.addItem(cartItem)

        // Show a toast message indicating the item has been added to the cart
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()

        // Update the UI to display the cart items with sizes
        displayCartItemsWithSizes()
    }

    private fun generateProductData(): List<Product> {
        val productList = ArrayList<Product>()

        productList.add(Product(
            "Air Jordan 1 Mid",
            "PHP8,395",
            R.drawable.shoe1,
            "Inspired by the original AJ1, this mid-top edition maintains the iconic look you love while choice colors and crisp leather give it a distinct identity.\n" +
                    "Shown: White/Black/Gym Red\n" +
                    "• Style: DQ8426-106\n" +
                    "Country/Region of Origin: Indonesia"
        ))

        productList.add(Product(
            "Jordan Max Aura 5",
            "PHP5,920",
            R.drawable.shoe2,
            "When you need a shoe that's ready 24/7, it's gotta be the Max Aura 5. Inspired by the AJ3, this pair of kicks puts a modern spin on the classic. They're made from durable leather and textiles that sit atop a heel of Nike Air cushioning so you can walk, run, or skate all day and still have fresh-feeling soles.\n" +
                    "Shown: Black/Bright Mandarin/Sail/Sky J Light Olive\n" +
                    "• Style: DZ4353-003\n" +
                    "Country/Region of Origin: Vietnam"
        ))

        productList.add(Product(
            "Nike Blazer Mid '77",
            "PHP5,095",
            R.drawable.shoe3,
            "Styled for the '70s. Loved in the '80s. Classic in the '90s. Ready for the future. The Blazer Mid '77 delivers a timeless design that's easy to wear. Its crisp leather upper breaks in beautifully and pairs with bold retro branding and suede accents for a premium feel. Exposed foam on the tongue and a special midsole finish make it look like you've just pulled them from the history books. Go ahead, perfect your outfit.\n" +
                    "Shown: White/Black\n" +
                    "Style: DO1344-101\n" +
                    "Country/Region of Origin: Vietnam"
        ))

        productList.add(Product(
            "Tatum 1",
            "PHP6,895",
            R.drawable.shoe4,
            "Your love for the game never fades. That's why the Tatum 1 was created with longevity in mind. Designed to carry you from the first to the fourth (and whatever OT comes up) as efficiently as possible, we stripped it down to the essentials-and made those essentials really, really good. The result is this season's lightest performance basketball shoe, with rubber only where it counts, a stress-tested foam midsole and an uncaged Nike Zoom Air unit for those explosive ups. Whatever stage of ball you're at, the Tatum 1 will keep you playing.\n" +
                    "Shown: White/Black/Green Strike/Total Orange\n" +
                    "Style: DZ3330-108\n" +
                    "Country/Region of Origin: Vietnam"
        ))

        productList.add(Product(
            "Nike Air Force 1 '07",
            "PHP5,495",
            R.drawable.shoe5w,
            "The radiance lives on in the Nike Air Force 1 '07, the basketball original that puts a fresh spin on what you know best: durably stitched overlays, clean finishes, and the perfect amount of flash to make you shine.\n" +
                    "Shown: Black/Black\n" +
                    "Style: CW2288-001\n" +
                    "Country/Region of Origin: Vietnam, India"
        ))

        productList.add(Product(
            "Blazer Mid '77 Vintage",
            "PHP4,990",
            R.drawable.shoe7,
            "In the '70s, Nike was the new shoe on the block. So new, in fact, we were still breaking into the basketball scene and testing prototypes on the feet of our local team. Of course, the design improved over the years, but the name stuck. The Nike Blazer Mid '77 Vintage-classic since the beginning.\n" +
                    "Shown: White/Black\n" +
                    "Style: BQ6806-100\n" +
                    "Country/Region of Origin: Vietnam, Indonesia, India"
        ))

        // Add more products similarly

        return productList
    }

    private fun getRelatedProducts(): List<Product> {
        // Placeholder implementation: Returns a list of related products based on the given product's name
        val relatedProducts = generateProductData().filter { it.name != productName }.take(6)
        return relatedProducts
    }

    private fun parsePrice(priceString: String): Double {
        // Remove PHP and commas, then parse to Double
        val cleanPriceString = priceString.replace("PHP", "").replace(",", "").trim()
        return cleanPriceString.toDoubleOrNull() ?: 0.0 // Default to 0.0 if parsing fails
    }

    private fun displayCartItemsWithSizes() {
        // Retrieve the cart items from the ShoppingCart
        val cartItems = getCartItems()

        // Clear previous content
        cartItemsTextView.text = ""

        // Iterate through the cart items and display them with sizes
        for (cartItem in cartItems) {
            val itemText = "Product: ${cartItem.name}, Price: ${cartItem.price}, Size: ${cartItem.size}\n"
            cartItemsTextView.append(itemText)
        }
    }

    private fun getCartItems(): List<CartItem> {
        // You need to implement this method to retrieve cart items from ShoppingCart class or database
        // For demonstration purposes, return an empty list
        return emptyList()
    }

    fun openCart(view: View) {
        // Navigate to the CartActivity
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    fun openSearch(view: View) {
        // Navigate to the SearchActivity
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}