package com.example.jamispropayl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var checkBoxReading: CheckBox
    private lateinit var checkBoxTraveling: CheckBox
    private lateinit var checkBoxCooking: CheckBox
    private lateinit var buttonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        editTextName = view.findViewById(R.id.editTextName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPhone = view.findViewById(R.id.editTextPhone)
        radioGroupGender = view.findViewById(R.id.radioGroupGender)
        checkBoxReading = view.findViewById(R.id.checkBoxReading)
        checkBoxTraveling = view.findViewById(R.id.checkBoxTraveling)
        checkBoxCooking = view.findViewById(R.id.checkBoxCooking)
        buttonSave = view.findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener { onSaveClick() }

        return view
    }

    private fun onSaveClick() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        // Get selected gender
        val selectedGenderId = radioGroupGender.checkedRadioButtonId
        val gender = if (selectedGenderId != -1) {
            val selectedRadioButton = view?.findViewById<RadioButton>(selectedGenderId)
            selectedRadioButton?.text.toString()
        } else {
            "Not specified"
        }

        // Get selected hobbies
        val hobbies = mutableListOf<String>()
        if (checkBoxReading.isChecked) hobbies.add(checkBoxReading.text.toString())
        if (checkBoxTraveling.isChecked) hobbies.add(checkBoxTraveling.text.toString())
        if (checkBoxCooking.isChecked) hobbies.add(checkBoxCooking.text.toString())

        // Show the collected information (for demonstration)
        val profileInfo = "Name: $name\nEmail: $email\nPhone: $phone\nGender: $gender\nHobbies: ${hobbies.joinToString(", ")}"
        Toast.makeText(requireContext(), profileInfo, Toast.LENGTH_LONG).show()

        // Clear the inputs after saving
        clearInputs()
    }

    private fun clearInputs() {
        editTextName.text.clear()
        editTextEmail.text.clear()
        editTextPhone.text.clear()
        radioGroupGender.clearCheck()
        checkBoxReading.isChecked = false
        checkBoxTraveling.isChecked = false
        checkBoxCooking.isChecked = false
    }
}
