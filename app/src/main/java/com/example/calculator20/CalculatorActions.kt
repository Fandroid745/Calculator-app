package com.example.calculator20

sealed class CalculatorActions {
    data class Number(val number:Int):CalculatorActions()
    object Clear:CalculatorActions()
    object Delete:CalculatorActions()
    object Decimal:CalculatorActions()
    object Calculate:CalculatorActions()
    data class Operations(val operation:CalculatorOperations):CalculatorActions()
}