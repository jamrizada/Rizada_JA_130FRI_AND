package com.example.jamispropayl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.DecimalFormat

class CalculatorFragment : Fragment() {

    private lateinit var display: TextView
    private var firstNumber: String = ""
    private var secondNumber: String = ""
    private var operator: String? = null
    private var isSecondNumber: Boolean = false
    private val decimalFormat = DecimalFormat("#.##")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        display = view.findViewById(R.id.tv_display)

        // Number buttons
        val numberButtons = listOf<Button>(
            view.findViewById(R.id.btn_0),
            view.findViewById(R.id.btn_1),
            view.findViewById(R.id.btn_2),
            view.findViewById(R.id.btn_3),
            view.findViewById(R.id.btn_4),
            view.findViewById(R.id.btn_5),
            view.findViewById(R.id.btn_6),
            view.findViewById(R.id.btn_7),
            view.findViewById(R.id.btn_8),
            view.findViewById(R.id.btn_9)
        )

        // Operator buttons
        val operatorButtons = mapOf(
            "+" to view.findViewById<Button>(R.id.btn_add),
            "-" to view.findViewById<Button>(R.id.btn_subtract),
            "*" to view.findViewById<Button>(R.id.btn_multiply),
            "/" to view.findViewById<Button>(R.id.btn_divide)
        )

        // Setting click listeners for number buttons
        for (button in numberButtons) {
            button.setOnClickListener { onNumberClick((it as Button).text.toString()) }
        }

        // Setting click listeners for operator buttons
        for ((op, button) in operatorButtons) {
            button.setOnClickListener { onOperatorClick(op) }
        }

        // Setting click listener for equals button
        view.findViewById<Button>(R.id.btn_equals).setOnClickListener { onEqualsClick() }
        // Setting click listener for clear button
        view.findViewById<Button>(R.id.btn_clear).setOnClickListener { onClearClick() }
        // Setting click listener for delete button
        view.findViewById<Button>(R.id.btn_delete).setOnClickListener { onDeleteClick() }
        // Setting click listener for dot button
        view.findViewById<Button>(R.id.btn_dot).setOnClickListener { onDotClick() }

        return view
    }

    private fun onNumberClick(number: String) {
        if (isSecondNumber) {
            // Add number to the second number
            if (secondNumber.length < 10) {
                secondNumber += number
                display.text = formatDisplay(secondNumber)
            }
        } else {
            // Add number to the first number
            if (firstNumber.length < 10) {
                firstNumber += number
                display.text = formatDisplay(firstNumber)
            }
        }
    }

    private fun onOperatorClick(op: String) {
        if (firstNumber.isNotEmpty()) {
            operator = op
            isSecondNumber = true
        }
    }

    private fun onEqualsClick() {
        if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && operator != null) {
            val result = calculate(firstNumber.toDouble(), secondNumber.toDouble(), operator!!)
            display.text = decimalFormat.format(result)
            firstNumber = result.toString()
            secondNumber = ""
            isSecondNumber = false
        }
    }

    private fun calculate(num1: Double, num2: Double, op: String): Double {
        return when (op) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> 0.0
        }
    }

    private fun onClearClick() {
        firstNumber = ""
        secondNumber = ""
        operator = null
        isSecondNumber = false
        display.text = "0"
    }

    private fun onDeleteClick() {
        if (isSecondNumber) {
            if (secondNumber.isNotEmpty()) {
                secondNumber = secondNumber.dropLast(1)
                display.text = if (secondNumber.isEmpty()) "0" else formatDisplay(secondNumber)
            }
        } else {
            if (firstNumber.isNotEmpty()) {
                firstNumber = firstNumber.dropLast(1)
                display.text = if (firstNumber.isEmpty()) "0" else formatDisplay(firstNumber)
            }
        }
    }

    private fun onDotClick() {
        // Check if it's the first number and if it can accept a decimal
        if (isSecondNumber) {
            if (!secondNumber.contains(".")) {
                secondNumber += "."
                display.text = formatDisplay(secondNumber)
            }
        } else {
            if (firstNumber.isEmpty() && !firstNumber.contains(".")) {
                // Prevent starting with a decimal
                return
            }
            if (!firstNumber.contains(".")) {
                firstNumber += "."
                display.text = formatDisplay(firstNumber)
            }
        }
    }

    private fun formatDisplay(value: String): String {
        return if (value.isNotEmpty()) {
            decimalFormat.format(value.toDouble())
        } else {
            "0"
        }
    }
}
