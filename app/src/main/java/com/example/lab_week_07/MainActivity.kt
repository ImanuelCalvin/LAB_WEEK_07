package com.example.lab_week_07

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Langsung buka MapsActivity
        startActivity(Intent(this, MapsActivity::class.java))
        finish()
    }
}
