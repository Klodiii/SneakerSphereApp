package com.sneakersphere.sneakersphereapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)
        val editProfileButton = findViewById<Button>(R.id.editProfileButton)
        val goBackToShopText = findViewById<TextView>(R.id.goBackToShopText)
        val ordersIcon = findViewById<ImageView>(R.id.ordersIcon)
        val favoritesIcon = findViewById<ImageView>(R.id.favoritesIcon)
        val settingsIcon = findViewById<ImageView>(R.id.settingsIcon)

        backButton.setOnClickListener {
            finish()
        }

        // Load and crop the square image to fit the circular ImageView
        Glide.with(this)
            .load(R.drawable.myself)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(profileImageView)

        editProfileButton.setOnClickListener {
            showEditProfileDialog()
        }

        goBackToShopText.setOnClickListener {
            // Navigate back to the dashboard activity
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
        ordersIcon.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        favoritesIcon.setOnClickListener {
            // Handle Favorites icon click
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        settingsIcon.setOnClickListener {
            // Handle Settings icon click
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }


    private fun showEditProfileDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_edit_profile, null)
        bottomSheetDialog.setContentView(view)

        // Handle save button click
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            // Save the profile information
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}
