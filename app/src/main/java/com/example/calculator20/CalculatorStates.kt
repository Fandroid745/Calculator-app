package com.example.calculator20.ui.theme

import com.example.calculator20.CalculatorOperations

data class CalculatorStates(
    val number1:String="",
    val number2:String="",
    val operation: CalculatorOperations?=null
)
