package com.example.news

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListFragment.OnNewspaperSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Load the ListFragment into the fragment_container
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }
    }

    // Callback when a newspaper is selected
    override fun onNewspaperSelected(newsItem: NewsItem) {
        val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putString("title", newsItem.headline)
                putString("content", newsItem.content)
            }
        }

        // Check if the landscape detail container exists
        if (findViewById<View>(R.id.detail_fragment_container) != null) {
            // Landscape mode: Add fragment to detail_fragment_container
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_fragment_container, detailFragment)
                .commit()
        } else {
            // Portrait mode: Replace fragment_container with the detail fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
