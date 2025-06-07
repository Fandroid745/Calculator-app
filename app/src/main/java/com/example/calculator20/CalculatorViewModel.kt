import androidx.lifecycle.ViewModel
import com.example.calculator20.CalculatorActions
import com.example.calculator20.CalculatorOperations
import com.example.calculator20.ui.theme.CalculatorStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.DecimalFormat

class CalculatorViewModel : ViewModel() {
    private var _state = MutableStateFlow(CalculatorStates())
    val stateFlow = _state.asStateFlow()

    fun onAction(action: CalculatorActions) {
        when (action) {
            is CalculatorActions.Number -> enterNumber(action.number)
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Clear -> _state.value = CalculatorStates()
            is CalculatorActions.Delete -> performDeletion()
            is CalculatorActions.Calculate -> performCalculation()
            is CalculatorActions.Pi -> applyPi()
            is CalculatorActions.Percentage -> applyPercentage() // <-- Add this handler
            is CalculatorActions.Operations -> {
                when (action.operation) {
                    CalculatorOperations.Factorial -> applyFactorial()
                    else -> enterOperations(action.operation)
                }
            }

            CalculatorActions.Parentheses -> TODO()
        }
    }



    private fun performCalculation() {
        val numbers = _state.value.number.map { it.toDoubleOrNull() ?: 0.0 }

        if (_state.value.currentInput.isNotBlank()) {
            _state.value = _state.value.copy(
                number = (_state.value.number + _state.value.currentInput).toMutableList(),
                currentInput = ""
            )
        }

        val updatedNumbers = _state.value.number.map { it.toDoubleOrNull() ?: 0.0 }
        val operations = _state.value.operations

        if (updatedNumbers.isEmpty()) return

        var result = updatedNumbers[0]
        for (i in operations.indices) {
            if (i + 1 >= updatedNumbers.size) break
            val nextNumber = updatedNumbers[i + 1]

            result = when (operations[i]) {
                CalculatorOperations.Add -> result + nextNumber
                CalculatorOperations.Subtract -> result - nextNumber
                CalculatorOperations.Multiply -> result * nextNumber
                CalculatorOperations.Divide -> if (nextNumber != 0.0) result / nextNumber else Double.NaN
                CalculatorOperations.Power -> Math.pow(result, nextNumber)
                CalculatorOperations.Percent -> result * (nextNumber / 100)
                CalculatorOperations.SquareRoot -> Math.sqrt(result)
                CalculatorOperations.PI -> result * Math.PI
                CalculatorOperations.Factorial -> factorial(result.toInt())

            }
        }

        val decimalFormat = DecimalFormat("#.########")
        val resultText = if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            decimalFormat.format(result)
        }

        _state.value = _state.value.copy(
            number = mutableListOf(resultText),
            operations = mutableListOf(),
            currentInput = ""
        )
    }



    private fun factorial(n: Int): Double {
        if (n < 0) return Double.NaN
        var result = 1.0
        for (i in 1..n) {
            result *= i
        }
        return result
    }

    private fun applyFactorial() {
        val value = _state.value.currentInput.toIntOrNull()
        if (value == null || value < 0) return

        val result = factorial(value)
        val resultText = if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            DecimalFormat("#.########").format(result)
        }

        _state.value = _state.value.copy(
            currentInput = resultText
        )
    }

    private fun applyPi() {
        val input = _state.value.currentInput.toDoubleOrNull()
        val result = if (input != null) input * Math.PI else Math.PI
        val resultText = if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            DecimalFormat("#.########").format(result)
        }

        _state.value = _state.value.copy(
            currentInput = resultText
        )
    }


    private fun performDeletion() {
        when {
            _state.value.currentInput.isNotBlank() -> {
                _state.value = _state.value.copy(
                    currentInput = _state.value.currentInput.dropLast(1)
                )
            }
            _state.value.operations.isNotEmpty() -> {
                _state.value = _state.value.copy(
                    operations = _state.value.operations.dropLast(1).toMutableList()
                )
            }
            _state.value.number.isNotEmpty() -> {
                _state.value = _state.value.copy(
                    number = _state.value.number.dropLast(1).toMutableList()
                )
            }
        }
    }

    private fun enterOperations(operation: CalculatorOperations) {
        if (_state.value.currentInput.isNotBlank()) {
            _state.value = _state.value.copy(
                number = (_state.value.number + _state.value.currentInput).toMutableList(),
                currentInput = ""
            )
        }
        _state.value = _state.value.copy(
            operations = (_state.value.operations + operation).toMutableList()
        )
    }

    private fun enterDecimal() {
        if (!_state.value.currentInput.contains(".")) {
            _state.value = _state.value.copy(
                currentInput = _state.value.currentInput + "."
            )
        }
    }

    private fun enterNumber(number: Int) {
        _state.value = _state.value.copy( // ✅ Fix
            currentInput = _state.value.currentInput + number.toString()
        )
    }
    private fun applyPercentage() {
        val input = _state.value.currentInput.toDoubleOrNull() ?: return
        val result = input / 100
        val resultText = if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            DecimalFormat("#.########").format(result)
        }

        _state.value = _state.value.copy(
            currentInput = resultText
        )
    }

    private fun toggleParentheses() {
        _state.value = _state.value.copy(
            currentInput = if (_state.value.currentInput.contains("(")) {
                _state.value.currentInput.replace("(", ")")
            } else {
                "(${_state.value.currentInput})"
            }
        )
    }
}
