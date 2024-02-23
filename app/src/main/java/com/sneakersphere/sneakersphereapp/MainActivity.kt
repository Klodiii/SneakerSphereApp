package com.sneakersphere.sneakersphereapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@MainActivity,SignupAndLogin::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}