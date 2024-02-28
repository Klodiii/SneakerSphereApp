package com.sneakersphere.sneakersphereapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var emailEditText: AppCompatEditText
    private lateinit var mobileNumberEditText: AppCompatEditText
    private lateinit var dateOfBirthEditText: AppCompatEditText
    private lateinit var countryEditText: AppCompatEditText
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        emailEditText = findViewById(R.id.emailEditText)
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText)
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText)
        countryEditText = findViewById(R.id.countryEditText)
        backButton = findViewById(R.id.backButton)

        // Set initial date for Date of Birth
        val initialDate = "0/00/00"
        dateOfBirthEditText.setText(initialDate)

        backButton.setOnClickListener {
            finish()
        }

        // Set onClickListener for the Log Out button
        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun saveSettings() {
        val email = emailEditText.text.toString()
        val mobileNumber = mobileNumberEditText.text.toString()
        val dateOfBirth = dateOfBirthEditText.text.toString()
        val country = countryEditText.text.toString()

        // Validate email and mobile number (not shown here)

        // Validate date of birth format
        val dateFormat = SimpleDateFormat("d/MM/yy", Locale.getDefault())
        try {
            dateFormat.parse(dateOfBirth)
            // Date format is valid, proceed with saving settings
            // For demonstration purposes, just show a toast message
            val message = "Settings saved:\nEmail: $email\nMobile Number: $mobileNumber\nDate of Birth: $dateOfBirth\nCountry: $country"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            // Date format is invalid
            Toast.makeText(this, "Invalid Date of Birth format. Please use format: d/MM/yy", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showLogoutConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Log Out")
            .setMessage("Are you sure? You'll need to login again to keep using the application.")
            .setPositiveButton("Log out") { _, _ ->
                // Handle logout action here
                // For example, you can clear user session and navigate to the login screen
                // Assuming SignUpAndLoginActivity is the login screen
                startActivity(Intent(this, SignupAndLogin::class.java))
                finish()
            }
            .setNegativeButton("Cancel", null)
            .create()
        alertDialog.show()
    }

}
