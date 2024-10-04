package com.example.jamispropayl

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class ListFragment : Fragment() {

    private lateinit var editTextItem: EditText
    private lateinit var btnSubmit: Button
    private lateinit var listViewItems: ListView
    private val itemList = mutableListOf<Item>() // Change to hold Item objects
    private lateinit var adapter: CustomListAdapter // Use your custom adapter

    private lateinit var gestureDetector: GestureDetector

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        editTextItem = view.findViewById(R.id.editTextItem)
        btnSubmit = view.findViewById(R.id.btn_submit)
        listViewItems = view.findViewById(R.id.listViewItems)

        // Initialize the custom adapter with itemList
        adapter = CustomListAdapter(requireContext(), itemList)
        listViewItems.adapter = adapter

        btnSubmit.setOnClickListener { onSubmitClick() }

        // Initialize GestureDetector for double-tap detection
        gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                val position = listViewItems.pointToPosition(e!!.x.toInt(), e.y.toInt())
                if (position != ListView.INVALID_POSITION) {
                    handleItemDoubleClick(position) // Handle double click
                }
                return true
            }
        })

        // Set OnTouchListener to capture touch events on the ListView
        listViewItems.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false // return false to allow other events to be handled
        }

        return view
    }

    private fun onSubmitClick() {
        val itemText = editTextItem.text.toString().trim()
        if (itemText.isNotEmpty()) {
            // Create a new item with a placeholder image (replace with your image resource)
            val newItem = Item(itemText, R.drawable.ic_profile, false)
            itemList.add(newItem) // Add Item object
            adapter.notifyDataSetChanged()
            editTextItem.text.clear()
        } else {
            Toast.makeText(requireContext(), "Please enter an item.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleItemDoubleClick(position: Int) {
        // Show dialog for edit or delete
        AlertDialog.Builder(requireContext())
            .setTitle("Choose an action")
            .setItems(arrayOf("Edit", "Delete")) { _, which ->
                when (which) {
                    0 -> showEditDialog(position) // Edit
                    1 -> deleteItem(position)       // Delete
                }
            }
            .show()
    }

    private fun showEditDialog(position: Int) {
        val currentItem = itemList[position]

        // Set up the input
        val input = EditText(requireContext())
        input.setText(currentItem.name)

        // Create the dialog
        AlertDialog.Builder(requireContext())
            .setTitle("Edit Item")
            .setView(input)
            .setPositiveButton("Update") { _, _ ->
                val updatedText = input.text.toString().trim()
                if (updatedText.isNotEmpty()) {
                    itemList[position] = currentItem.copy(name = updatedText) // Update item name
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Item cannot be empty.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteItem(position: Int) {
        itemList.removeAt(position)
        adapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Item deleted.", Toast.LENGTH_SHORT).show()
    }
}
