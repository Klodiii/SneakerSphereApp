package com.sneakersphere.sneakersphereapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MaleCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_male_category)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val backButton: View = findViewById(R.id.backButtonMen)
        backButton.setOnClickListener {
            finish()
        }
    }
}