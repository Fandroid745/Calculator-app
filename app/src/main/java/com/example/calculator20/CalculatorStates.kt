package com.example.calculator20.ui.theme

import com.example.calculator20.CalculatorOperations


data class CalculatorStates(
    val number: List<String> = emptyList(),
    val operations: List<CalculatorOperations> = emptyList(),
    val currentInput: String = ""
)
