    package com.example.calculator20
    import android.media.VolumeShaper
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import com.example.calculator20.ui.theme.CalculatorStates
    import java.text.DecimalFormat

    class CalculatorViewModel:ViewModel() {
        var state by mutableStateOf(CalculatorStates())
            private set

        private var openParenthesesCount=0

        fun onAction(action: CalculatorActions) {
            when (action) {
                is CalculatorActions.Number -> enterNumber(action.number)
                is CalculatorActions.Decimal -> enterDecimal()
                is CalculatorActions.Clear -> state = CalculatorStates()
                is CalculatorActions.Operations -> enterOperations(action.operation)
                is CalculatorActions.Calculate -> performCalculation()
                is CalculatorActions.Delete -> performDeletion()
                is CalculatorActions.Parentheses -> toggleParentheses()
                else -> return

            }
        }

        private fun performCalculation() {
            val number1 = state.number1.toDoubleOrNull()
            val number2 = state.number2.toDoubleOrNull()

            if (number1 != null) {
                val result = when(state.operation) {
                    is CalculatorOperations.Add -> number1 + (number2 ?: 0.0)
                    is CalculatorOperations.Subtract -> number1 - (number2 ?:0.0)
                    is CalculatorOperations.Multiply -> number1 * (number2?:1.0)
                    is CalculatorOperations.Divide -> if(number2!=null && number2!=0.0)number1/number2 else Double.NaN
                    is CalculatorOperations.Power -> Math.pow(number1, number2 ?:1.0) // number1 raised to number2
                    is CalculatorOperations.Percent -> number1 * (number2!! / 100)
                    is CalculatorOperations.SquareRoot -> Math.sqrt(number1)
                    is CalculatorOperations.PI -> number1 *Math.PI
                    is CalculatorOperations.Factorial -> factorial(number1.toInt())


                    null -> return
                }
                val decimalFormat= DecimalFormat("#.########")
                val resultText = if (result % 1 == 0.0) {
                    result.toInt().toString()
                }else {
                    decimalFormat.format(result)
                }
                state = state.copy(
                    number1 = resultText,
                    number2 ="",
                    operation = null
                )
            }
        }



        private fun factorial(n: Int): Double {
            return if (n == 0) 1.0 else n * factorial(n - 1)
        }



        private fun performDeletion() {
            when {
                state.number2.isNotBlank() -> state = state.copy(
                    number2 = state.number2.dropLast(1)
                )

                state.operation != null -> state = state.copy(
                    operation = null
                )
                state.number1.isNotBlank() -> state=state.copy(

                    number1 = state.number1.dropLast(1)
                )
            }
        }


        private fun enterOperations(operations: CalculatorOperations) {
            if (state.number1.isNotBlank()) {
                state = state.copy(operation = operations)


                if (operations is CalculatorOperations.SquareRoot ||
                    operations is CalculatorOperations.PI ||
                    operations is CalculatorOperations.Factorial) {
                    performCalculation()
                }
            }
        }

        private fun enterDecimal() {
            if (state.operation == null && !state.number1.contains(".")
                && state.number1.isNotBlank()

            ) {
                state = state.copy(
                    number1 = state.number1 + "."
                )
                return
            }
            if (!state.number2.contains(".") && state.number2.isNotBlank()
            ) {
                state = state.copy(
                    number1 = state.number1 + ""
                )
            }
        }

            private fun enterNumber (number:Int) {
                if (state.operation == null) {
                    if (state.number1.length >= MAX_NUM_LENGTH) {
                        return
                    }
                    state=state.copy(
                        number1=state.number1+number
                    )
                    return
                }
                if(state.number2.length>=MAX_NUM_LENGTH) {
                    return
                }

                state=state.copy(
                    number2=state.number2+number
                )
            }

       private fun toggleParentheses() {
           if (openParenthesesCount == 0 || lastCharacterIsOperator()) {
               state = state.copy(number1 = state.number1 + "(")
               openParenthesesCount++
           } else if (openParenthesesCount > 0) {
               state = state.copy(number1 = state.number1+ ")")
               openParenthesesCount--
           }
       }



    private fun lastCharacterIsOperator():Boolean {
        val lastChar=state.number1.lastOrNull()
        return lastChar == '+'|| lastChar =='-' || lastChar=='*'|| lastChar=='/' || lastChar =='^'
    }

    companion object{
        private const val MAX_NUM_LENGTH=8
    }
    }

