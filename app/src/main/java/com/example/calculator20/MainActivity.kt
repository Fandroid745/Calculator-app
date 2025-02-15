package com.example.calculator20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator20.ui.theme.Blue
import com.example.calculator20.ui.theme.Calculator20Theme
import com.example.calculator20.ui.theme.CalculatorStates
import com.example.calculator20.ui.theme.LightGray
import com.example.calculator20.ui.theme.MediumGray

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator20Theme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state
                val buttonSpacing = 8.dp

                Calculator(
                    state = state,
                    buttonSpacing = buttonSpacing,
                    onAction = { action -> viewModel.onAction(action) }
                )
            }
        }
    }

    @Composable
    fun Calculator(
        state: CalculatorStates,
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
                // Display Screen
                Text(
                    text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    fontSize = 36.sp,
                    color = Color.White,
                    maxLines = 2
                )

                // Additional Buttons Row: √, π, ^, !
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("√", LightGray, Modifier.weight(1f), isShaped = false) {
                        onAction(CalculatorActions.Operations(CalculatorOperations.SquareRoot))
                    }
                    CalculatorButton("π", LightGray, Modifier.weight(1f), isShaped = false) {
                        onAction(CalculatorActions.Pi)
                    }
                    CalculatorButton("^", LightGray, Modifier.weight(1f), isShaped = false) {
                        onAction(CalculatorActions.Operations(CalculatorOperations.Power))
                    }
                    CalculatorButton("!", LightGray, Modifier.weight(1f), isShaped = false) {
                        onAction(CalculatorActions.Operations(CalculatorOperations.Factorial))
                    }
                }

                // Top Row: AC, Del, %, ÷
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("AC", LightGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Clear
                        )
                    }
                    CalculatorButton("Del", LightGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Delete
                        )
                    }
                    CalculatorButton("%", LightGray, Modifier.weight(1f)) {
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

                // Third Row: 4, 5, 6, -
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton("4", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(4)
                        )
                    }
                    CalculatorButton("5", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(5)
                        )
                    }
                    CalculatorButton("6", MediumGray, Modifier.weight(1f)) {
                        onAction(
                            CalculatorActions.Number(6)
                        )
                    }
                    CalculatorButton(
                        "-",
                        Blue,
                        Modifier.weight(1f)
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
                    CalculatorButton("0", MediumGray, Modifier.weight(1f) ,isSmall=true) {
                        onAction(
                            CalculatorActions.Number(0)
                        )
                    }
                    CalculatorButton(".", MediumGray, Modifier.weight(1f),isSmall=true) {
                        onAction(
                            CalculatorActions.Decimal
                        )
                    }
                    CalculatorButton(
                        "=",
                        Blue,
                        Modifier.weight(1f),
                        isSmall=true
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
        isSmall:Boolean=false,
        onClick: () -> Unit
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .aspectRatio(if(isSmall)1.5f else 1f )
                .clip(if (isShaped) CircleShape else MaterialTheme.shapes.medium)
                .background(color)
                .clickable { onClick() }
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
}