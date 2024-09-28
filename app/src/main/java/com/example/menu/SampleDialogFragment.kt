package com.example.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class SampleDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialog, container, false)

        val titleTextView: TextView = view.findViewById(R.id.dialog_title)
        val messageTextView: TextView = view.findViewById(R.id.dialog_message)
        val positiveButton: Button = view.findViewById(R.id.button_positive)
        val negativeButton: Button = view.findViewById(R.id.button_negative)

        titleTextView.text = "Dialog Title"
        messageTextView.text = "This is the dialog message."

        positiveButton.setOnClickListener {
            // Replace with the fragment when the positive button is clicked
            (activity as MainActivity).navigateToFragment(SampleFragment())
            dismiss()
        }

        negativeButton.setOnClickListener {
            // Exit the app
            activity?.finish()
        }

        return view
    }
}
