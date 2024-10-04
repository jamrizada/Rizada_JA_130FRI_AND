package com.example.jamispropayl

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class CustomListAdapter(
    context: Context,
    private val items: List<Item>
) : ArrayAdapter<Item>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        // Inflate the custom layout for each item
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

        // Get references to the views
        val checkbox = view.findViewById<CheckBox>(R.id.listcheck)
        val imageView = view.findViewById<ImageView>(R.id.listimage)
        val textView = view.findViewById<TextView>(R.id.listtext)

        // Set the checkbox state
        checkbox.isChecked = item?.isChecked ?: false
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            item?.isChecked = isChecked // Update the item state
        }

        // Set the image and text
        imageView.setImageResource(item?.imageResId ?: 0) // Set the image resource
        textView.text = item?.name ?: "" // Set the item name

        return view // Return the completed view to render on screen
    }
}

// Data class to represent each item in the ListView
data class Item(val name: String, val imageResId: Int, var isChecked: Boolean)
