package com.example.jamispropayl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Initialize fragments
    private val calculatorFragment = CalculatorFragment()
    private val listFragment = ListFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment = profileFragment // Default fragment

            when (item.itemId) {
                R.id.nav_calculator -> selectedFragment = calculatorFragment
                R.id.nav_list -> selectedFragment = listFragment
                R.id.nav_profile -> selectedFragment = profileFragment
            }

            // Replace fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, selectedFragment)
                .commit()

            true
        }

        // Set default selection to Profile Fragment
        bottomNavigationView.selectedItemId = R.id.nav_profile
    }
}
