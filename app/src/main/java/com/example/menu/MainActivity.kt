package com.example.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Title ni"
        supportActionBar?.subtitle = "Subtitle ni"

        if (savedInstanceState == null) {
            navigateToFragment(MenuFragment())
        }
    }

    fun onIconClick(view: View) {
        Log.d("MainActivity", "Icon clicked: ${view.id}")  // Log to see which icon is clicked
        when (view.id) {
            R.id.layout_fragment -> {
                Log.d("MainActivity", "Fragment clicked")
                navigateToFragment(SampleFragment()) // Call the method to navigate to the fragment
            }
            R.id.layout_dialog -> {
                Log.d("MainActivity", "Dialog clicked")
                val dialog = SampleDialogFragment()
                dialog.show(supportFragmentManager, "SampleDialog")
            }
            R.id.layout_exit -> {
                Log.d("MainActivity", "Exit clicked")
                showPopupMenu(view)
            }
        }
    }

    fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Ensure this ID matches your layout
            .addToBackStack(null)
            .commit()
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.exit_menu, popup.menu) // Inflate your submenu layout
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.exit_app -> {
                    finish() // Close the app
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
