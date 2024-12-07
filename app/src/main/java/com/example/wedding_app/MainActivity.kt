package com.example.wedding_app

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Simulate a splash screen delay
        Thread {
            Thread.sleep(1500) // 1.5 seconds delay (optional)

            // Check user role and navigate accordingly
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val userRole = sharedPreferences.getString("USER_ROLE", null)

            val intent = when (userRole) {
                "Manager" -> Intent(this, DashboardActivity::class.java)
                "Customer" -> Intent(this, ViewEventsActivity::class.java)
                else -> Intent(this, LoginActivity::class.java) // Default to Login
            }

            startActivity(intent)
            finish()
        }.start()
    }
}
