package com.example.news

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment(R.layout.fragment_list) {

    private var listener: OnNewspaperSelectedListener? = null

    interface OnNewspaperSelectedListener {
        fun onNewspaperSelected(newsItem: NewsItem)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is OnNewspaperSelectedListener) {
            context
        } else {
            throw RuntimeException("$context must implement OnNewspaperSelectedListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MyItemRecyclerViewAdapter(newsData) { newsItem ->
            listener?.onNewspaperSelected(newsItem)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
