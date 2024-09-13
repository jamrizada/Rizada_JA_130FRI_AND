package com.example.rizadalistview

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var submitButton: Button
    private lateinit var adapter: ItemAdapter
    private val itemList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        submitButton = findViewById(R.id.submitButton)

        adapter = ItemAdapter(this, itemList)
        listView.adapter = adapter

        submitButton.setOnClickListener {
            val text = editText.text.toString()
            if (text.isNotEmpty()) {
                itemList.add(text)
                adapter.notifyDataSetChanged()
                editText.text.clear()
            }
        }
    }
}
