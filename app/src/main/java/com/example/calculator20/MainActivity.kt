package com.example.calculator20

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

                // Call the Calculator composable here
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
        buttonSpacing: Dp = (8.dp),
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
                    text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    fontSize = 80.sp,
                    color = Color.White,
                    maxLines = 2
                )

                // First Row: AC, DEL, ^, /
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "AC",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Clear) }
                    )
                    CalculatorButton(
                        symbol = "Del",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Delete) }
                    )
                  CalculatorButton(
                        symbol = "/",
                        modifier = Modifier
                            .background(Blue)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Operations(CalculatorOperations.Divide)) }
                    )
                }

                // Second Row: ( , ) , Pi, √, x^Y, !
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {

                    CalculatorButton(
                        symbol = "π",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Operations(CalculatorOperations.PI)) }
                    )
                    CalculatorButton(
                        symbol = "√",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Operations(CalculatorOperations.SquareRoot)) }
                    )
                    CalculatorButton(
                        symbol = "^",
                        modifier = Modifier
                            .background(Blue)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Operations(CalculatorOperations.Power)) }
                    )
                    CalculatorButton(
                        symbol = "!",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Operations(CalculatorOperations.Factorial)) }
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "7",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(7)) }
                    )
                    CalculatorButton(
                        symbol = "8",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(8)) }
                    )
                    CalculatorButton(
                        symbol = "9",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(9)) }
                    )
                    CalculatorButton(
                        symbol = "X",
                        modifier = Modifier
                            .background(Blue)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Operations(CalculatorOperations.Multiply)) }
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "4",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(4)) }
                    )
                    CalculatorButton(
                        symbol = "5",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(5)) }
                    )
                    CalculatorButton(
                        symbol = "6",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(6)) }
                    )
                    CalculatorButton(
                        symbol = "-",
                        modifier = Modifier
                            .background(Blue)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Operations(CalculatorOperations.Subtract)) }
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "1",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(1)) }
                    )
                    CalculatorButton(
                        symbol = "2",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(2)) }
                    )
                    CalculatorButton(
                        symbol = "3",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Number(3)) }
                    )
                    CalculatorButton(
                        symbol = "+",
                        modifier = Modifier
                            .background(Blue)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Operations(CalculatorOperations.Add)) }
                    )
                }

                // Last Row: 0, ., =
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "0",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(2f)
                            .weight(2f),
                        onClick = { onAction(CalculatorActions.Number(0)) }
                    )
                    CalculatorButton(
                        symbol = ".",
                        modifier = Modifier
                            .background(MediumGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Decimal) }
                    )
                    CalculatorButton(
                        symbol = "=",
                        modifier = Modifier
                            .background(Blue)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorActions.Calculate) }
                    )
                }
            }
        }
    }

}