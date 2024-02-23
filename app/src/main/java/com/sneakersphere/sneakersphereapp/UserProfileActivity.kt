package com.sneakersphere.sneakersphereapp

// UserProfileActivity.kt
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)

        backButton.setOnClickListener {
            finish()
        }

        // Load and crop the square image to fit the circular ImageView
        Glide.with(this)
            .load(R.drawable.myself)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(profileImageView)
    }

}
