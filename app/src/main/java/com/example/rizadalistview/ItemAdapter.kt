package com.example.rizadalistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog

class ItemAdapter(private val context: Context, private val items: MutableList<String>) : BaseAdapter() {

    private var lastClickTime: Long = 0

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textView = view.findViewById<TextView>(R.id.itemText)
        val imageView = view.findViewById<ImageView>(R.id.itemImage)

        textView.text = items[position]

        view.setOnClickListener {
            handleDoubleClick(position)
        }

        return view
    }

    private fun handleDoubleClick(position: Int) {
        val currentTime = System.currentTimeMillis()
        val DOUBLE_CLICK_TIME_DELTA: Long = 300 // milliseconds

        if (currentTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            // It's a double click, show the options dialog
            showOptionsDialog(position)
        }
        lastClickTime = currentTime
    }

    private fun showOptionsDialog(position: Int) {
        val options = arrayOf("Edit", "Delete")
        AlertDialog.Builder(context)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditDialog(position)
                    1 -> deleteItem(position)
                }
            }
            .show()
    }

    private fun showEditDialog(position: Int) {
        val editText = EditText(context)
        editText.setText(items[position])
        AlertDialog.Builder(context)
            .setTitle("Edit Item")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                items[position] = editText.text.toString()
                notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyDataSetChanged()
    }
}
