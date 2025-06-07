package com.example.calculator20

sealed class CalculatorOperations(val symbol: String) {


    object Add : CalculatorOperations("+")
    object Subtract : CalculatorOperations("-")
    object Multiply : CalculatorOperations("*")
    object Divide : CalculatorOperations("/")

    object Power : CalculatorOperations("^")

    object PI: CalculatorOperations("π")
    object Percent : CalculatorOperations("%")  // Percent
    object SquareRoot : CalculatorOperations("√") // Square root


    object Factorial : CalculatorOperations("!")
}
