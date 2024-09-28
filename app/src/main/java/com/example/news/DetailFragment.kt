package com.example.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val titleTextView = view.findViewById<TextView>(R.id.news_title)
        val contentTextView = view.findViewById<TextView>(R.id.news_content)

        arguments?.let {
            titleTextView.text = it.getString("title")
            contentTextView.text = it.getString("content")
        }

        return view
    }
}
