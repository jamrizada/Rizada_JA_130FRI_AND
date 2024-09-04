package com.example.rizada_calcu

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var operationDisplay: TextView
    private val decimalFormat = DecimalFormat("#.##").apply {
        roundingMode = RoundingMode.HALF_UP
    }

    private var firstOperand: BigDecimal? = null
    private var secondOperand: BigDecimal? = null
    private var currentOperation: String? = null
    private var isOperatorPressed = false
    private var isResultDisplayed = false
    private var decimalCount = 0

    companion object {
        private const val DISPLAY_TEXT = "display_text"
        private const val OPERATION_TEXT = "operation_text"
        private const val FIRST_OPERAND = "first_operand"
        private const val SECOND_OPERAND = "second_operand"
        private const val OPERATION = "operation"
        private const val OPERATOR_PRESSED = "operator_pressed"
        private const val RESULT_DISPLAYED = "result_displayed"
        private const val DECIMAL_COUNT = "decimal_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        operationDisplay = findViewById(R.id.operation_display)

        setupButtons()
        restoreState(savedInstanceState)
    }

    private fun setupButtons() {
        val buttonMap = mapOf(
            R.id.button_clear_all to { clearAll() },
            R.id.button_delete_last to { removeLastCharacter() },
            R.id.button_divide to { setOperation("÷") },
            R.id.button_multiply to { setOperation("×") },
            R.id.button_subtract to { setOperation("-") },
            R.id.button_add to { setOperation("+") },
            R.id.button_equals to { computeResult() },
            R.id.button_decimal to { addDecimal() }
        )

        buttonMap.forEach { (id, action) ->
            findViewById<Button>(id).setOnClickListener {
                Log.d("Calculator", "Button pressed: ${resources.getResourceEntryName(id)}")
                action()
            }
        }

        for (i in 0..9) {
            val buttonId = resources.getIdentifier("button_$i", "id", packageName)
            findViewById<Button>(buttonId).setOnClickListener { appendNumber(i.toString()) }
        }
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            display.text = it.getString(DISPLAY_TEXT, "0")
            operationDisplay.text = it.getString(OPERATION_TEXT, "")
            firstOperand = it.getSerializable(FIRST_OPERAND) as? BigDecimal
            secondOperand = it.getSerializable(SECOND_OPERAND) as? BigDecimal
            currentOperation = it.getString(OPERATION)
            isOperatorPressed = it.getBoolean(OPERATOR_PRESSED, false)
            isResultDisplayed = it.getBoolean(RESULT_DISPLAYED, false)
            decimalCount = it.getInt(DECIMAL_COUNT, 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(DISPLAY_TEXT, display.text.toString())
        outState.putString(OPERATION_TEXT, operationDisplay.text.toString())
        outState.putSerializable(FIRST_OPERAND, firstOperand)
        outState.putSerializable(SECOND_OPERAND, secondOperand)
        outState.putString(OPERATION, currentOperation)
        outState.putBoolean(OPERATOR_PRESSED, isOperatorPressed)
        outState.putBoolean(RESULT_DISPLAYED, isResultDisplayed)
        outState.putInt(DECIMAL_COUNT, decimalCount)
    }

    private fun clearAll() {
        firstOperand = null
        secondOperand = null
        currentOperation = null
        display.text = "0"
        operationDisplay.text = ""
        isOperatorPressed = false
        isResultDisplayed = false
        decimalCount = 0
    }

    private fun removeLastCharacter() {
        val currentText = display.text.toString()
        if (currentText.isNotEmpty()) {
            val newText = currentText.dropLast(1).takeIf { it.isNotEmpty() } ?: "0"
            display.text = if (newText.endsWith(".")) {
                decimalCount--
                newText
            } else {
                newText
            }
        }
    }

    private fun addDecimal() {
        if (decimalCount == 0) {
            appendNumber(".")
        }
    }

    private fun setOperation(operation: String) {
        if (isResultDisplayed) {
            firstOperand = BigDecimal(display.text.toString())
            isResultDisplayed = false
        } else if (firstOperand == null) {
            firstOperand = BigDecimal(display.text.toString())
        } else {
            secondOperand = BigDecimal(display.text.toString())
            computeResult() // Compute the result with the previous operand and operation
            firstOperand = BigDecimal(display.text.toString()) // Update the first operand to the new value
        }

        operationDisplay.text = "${display.text} $operation"
        currentOperation = operation
        isOperatorPressed = true
        decimalCount = 0
    }

    private fun appendNumber(number: String) {
        if (isOperatorPressed || isResultDisplayed) {
            display.text = number
            isOperatorPressed = false
            isResultDisplayed = false
        } else {
            val currentText = display.text.toString()
            if (currentText == "0" && number != ".") {
                display.text = number
            } else {
                val (wholePart, decimalPart) = currentText.split(".").let { it[0] to (it.getOrNull(1) ?: "") }
                when {
                    number == "." && decimalCount == 0 && wholePart.length <= 2 -> {
                        display.text = "$currentText."
                        decimalCount++
                    }
                    decimalCount == 0 && wholePart.length < 2 -> {
                        display.text = "$currentText$number"
                    }
                    decimalCount > 0 && decimalPart.length < 2 -> {
                        display.text = "$currentText$number"
                    }
                }
            }
        }
    }


    private fun computeResult() {
        Log.d("Calculator", "Computing result with firstOperand: $firstOperand, secondOperand: $secondOperand, operation: $currentOperation")

        // Check if we need to set secondOperand before computation
        if (firstOperand != null && currentOperation != null) {
            if (secondOperand == null) {
                secondOperand = BigDecimal(display.text.toString())
            }
            val result = when (currentOperation) {
                "+" -> firstOperand!!.add(secondOperand!!)
                "-" -> firstOperand!!.subtract(secondOperand!!)
                "×" -> firstOperand!!.multiply(secondOperand!!)
                "÷" -> if (secondOperand == BigDecimal.ZERO) {
                    BigDecimal.ZERO // Handle division by zero
                } else {
                    firstOperand!!.divide(secondOperand!!, 2, RoundingMode.HALF_UP)
                }
                else -> BigDecimal.ZERO
            }
            display.text = formatNumber(result)
            operationDisplay.text = ""
            firstOperand = result
            secondOperand = null
            currentOperation = null
            isOperatorPressed = false
            isResultDisplayed = true
            decimalCount = 0
        } else {
            Log.e("Calculator", "Compute result failed: Operand or operation is null")
        }
    }


    private fun formatNumber(number: BigDecimal): String {
        return decimalFormat.format(number)
    }
}
