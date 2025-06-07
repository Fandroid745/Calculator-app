package com.example.calculator20

import CalculatorViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator20.ui.theme.Blue
import com.example.calculator20.ui.theme.Calculator20Theme
import com.example.calculator20.ui.theme.CalculatorStates
import com.example.calculator20.ui.theme.MediumGray

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator20Theme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.stateFlow.collectAsState()
                val buttonSpacing = 8.dp

                Calculator(
                    state = state,
                    buttonSpacing = buttonSpacing
                ) { action ->
                    println("Button onclicked :$action")
                    viewModel.onAction(action)
                }
            }
        }
    }

    @Composable
    fun Calculator(
        state: State<CalculatorStates>,
        modifier: Modifier = Modifier,
        buttonSpacing: Dp = 8.dp,
        onAction: (CalculatorActions) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {

                Text(
                    text = "${state.value.number.joinToString(" ")} ${state.value.operations.joinToString(" ") { it.symbol }}",
                    fontSize = 20.sp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Display Screen
                Text(
                    text = buildDisplayText(state.value),
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    fontSize = if (buildDisplayText(state.value).length > 10) 28.sp else 36.sp,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Additional Buttons Row: √, π, ^, !
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("√", MediumGray, Modifier.weight(1f), isShaped = false) {
                        onAction(CalculatorActions.Operations(CalculatorOperations.SquareRoot))
                    }
                    CalculatorButton("π", MediumGray, Modifier.weight(1f), isShaped = false) {
                        onAction(CalculatorActions.Pi)
                    }
                    CalculatorButton("^", MediumGray, Modifier.weight(1f), isShaped = false) {
                        onAction(CalculatorActions.Operations(CalculatorOperations.Power))
                    }
                    CalculatorButton("!", MediumGray, Modifier.weight(1f), isShaped = false) {
                        onAction(CalculatorActions.Operations(CalculatorOperations.Factorial))
                    }
                }

                // Top Row: AC, Del, %, ÷
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("AC", Blue, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Clear
                        )
                    }
                    CalculatorButton("Del", Blue, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Delete
                        )
                    }
                    CalculatorButton("%", Blue, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Percentage
                        )
                    }
                    CalculatorButton(
                        "÷",
                        Blue,
                        Modifier.weight(1f)
                    ) { onAction(CalculatorActions.Operations(CalculatorOperations.Divide)) }
                }

                // Second Row: 7, 8, 9, ×
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("7", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(7)
                        )
                    }
                    CalculatorButton("8", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(8)
                        )
                    }
                    CalculatorButton("9", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(9)
                        )
                    }
                    CalculatorButton(
                        "×",
                        Blue,
                        Modifier.weight(1f)
                    ) { onAction(CalculatorActions.Operations(CalculatorOperations.Multiply)) }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("4", MediumGray, Modifier.weight(1f)) {
                        onAction(CalculatorActions.Number(4))
                    }
                    CalculatorButton("5", MediumGray, Modifier.weight(1f)) {
                        onAction(CalculatorActions.Number(5))
                    }
                    CalculatorButton("6", MediumGray, Modifier.weight(1f)) {
                        onAction(CalculatorActions.Number(6))
                    }
                    CalculatorButton(
                        "-",
                        Blue,
                        Modifier.weight(1f) // Fixed: Added closing parenthesis
                    ) { onAction(CalculatorActions.Operations(CalculatorOperations.Subtract)) }
                }

                // Fourth Row: 1, 2, 3, +
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("1", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(1)
                        )
                    }
                    CalculatorButton("2", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(2)
                        )
                    }
                    CalculatorButton("3", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(3)
                        )
                    }
                    CalculatorButton(
                        "+",
                        Blue,
                        Modifier.weight(1f)
                    ) { onAction(CalculatorActions.Operations(CalculatorOperations.Add)) }
                }

                // Bottom Row: 0, ., =
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("0", MediumGray, Modifier.weight(1f), isSmall = true) {
                        onAction(
                            CalculatorActions.Number(0)
                        )
                    }
                    CalculatorButton(".", MediumGray, Modifier.weight(1f), isSmall = true) {
                        onAction(
                            CalculatorActions.Decimal
                        )
                    }
                    CalculatorButton(
                        "=",
                        Blue,
                        Modifier.weight(1f),
                        isSmall = true
                    ) { onAction(CalculatorActions.Calculate) }
                }
            }
        }
    }

    @Composable
    fun CalculatorButton(
        symbol: String,
        color: Color,
        modifier: Modifier = Modifier,
        isShaped: Boolean = true,
        isSmall: Boolean = false,
        onClick: () -> Unit
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .aspectRatio(if (isSmall) 1.5f else 1f)
                .clip(if (isShaped) CircleShape else MaterialTheme.shapes.medium)
                .background(color)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current
                ) { onClick() }
                .semantics { contentDescription = symbol }
        ) {
            Text(
                text = symbol,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }

    private fun buildDisplayText(state: CalculatorStates): String {
        return when {
            state.currentInput.isNotBlank() -> state.currentInput
            state.number.isNotEmpty() -> state.number.last()
            else -> "0"

        }
    }
}


