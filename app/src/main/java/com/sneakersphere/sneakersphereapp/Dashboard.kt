package com.sneakersphere.sneakersphereapp

import android.content.Intent
import xxandroid.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class Dashboard : AppCompatActivity(), ProductAdapter.OnProductClickListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        val productRecyclerView = findViewById<RecyclerView>(R.id.productRecyclerView)

        val itemList = generateDummyData()
        val adapter = CarouselAdapter(itemList)

        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * resources.displayMetrics.density).toInt()))
        viewPager2.setPageTransformer(compositePageTransformer)

        val ProductAPI= RetrofitHelper.getInstance().create(ProductAPI::class.java)

        GlobalScope.launch {
            val result = ProductAPI.getProduct()
            if (result)
        }

        val delayMillis: Long = 3000
        val initialDelayMillis: Long = 0
        val scrollRunnable = Runnable {
            val nextItem = (viewPager2.currentItem + 1) % itemList.size
            viewPager2.setCurrentItem(nextItem, true)
        }

        val handler = Handler()
        handler.postDelayed(scrollRunnable, initialDelayMillis)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed(scrollRunnable, delayMillis)
            }
        })

        val spanCount = 2
        val layoutManager = GridLayoutManager(this, spanCount)
        productRecyclerView.layoutManager = layoutManager

        val productAdapter = ProductAdapter(generateProductData(), this)
        productRecyclerView.adapter = productAdapter

        val searchButton: ImageButton = findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivityForResult(intent, SEARCH_REQUEST_CODE)
        }

        // Categories
        val maleCategory = findViewById<LinearLayout>(R.id.maleCategory)
        val femaleCategory = findViewById<LinearLayout>(R.id.femaleCategory)
        val kidsCategory = findViewById<LinearLayout>(R.id.kidsCategory)

        // Set onClickListener for Men Category
        maleCategory.setOnClickListener {
            handleCategoryClick("Men")
        }

        // Set onClickListener for Women Category
        femaleCategory.setOnClickListener {
            handleCategoryClick("Women")
        }

        // Set onClickListener for Kids Category
        kidsCategory.setOnClickListener {
            handleCategoryClick("Kids")
        }

        // Set onClickListener for the menu button to open the drawer
        val menuButton: ImageButton = findViewById(R.id.menuButton)
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }

        // Set onClickListener for the profile image in the drawer header
        val profileImageView = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.profileImageView)
        profileImageView.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        // Set onClickListener for the username in the drawer header
        val usernameTextView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.usernameTextView)
        usernameTextView.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle Home click
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_orders -> {
                    // Handle Orders click
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_favorites -> {
                    // Handle Favorites click
                    val intent = Intent(this, FavoritesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    // Handle Settings click
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


        val cartButton: ImageButton = findViewById(R.id.cartButton)
        cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleCategoryClick(category: String) {
        // Handle category click, navigate to a new activity based on the category
        when (category) {
            "Men" -> {
                // activity for Men category
                val intent = Intent(this, MaleCategoryActivity::class.java)
                startActivity(intent)
            }
            "Women" -> {
                // activity for Women category
                val intent = Intent(this, FemaleCategoryActivity::class.java)
                startActivity(intent)
            }
            "Kids" -> {
                // activity for Kids category
                val intent = Intent(this, KidsCategoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun generateDummyData(): List<Int> {
        return listOf(
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7
        )
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

    override fun onProductClick(product: Product) {
        // Handle product click, for example, show a product description
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("productName", product.name)
        intent.putExtra("productPrice", product.price)
        intent.putExtra("productImage", product.imageResource)
        intent.putExtra("productDescription", product.description)
        startActivity(intent)
    }

    companion object {
        const val SEARCH_REQUEST_CODE = 123
    }
}
