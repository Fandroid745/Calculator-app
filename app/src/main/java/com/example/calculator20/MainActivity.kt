package com.example.calculator20

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator20.ui.theme.Calculator20Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                  Calculator20Theme{
                      val viewModel = viewModel<CalculatorViewModel>()
                      val state = viewModel.state
                      val buttonSpacing = 8.dp
                      Calculator(
                          state = state,
                          onAction = viewModel::onAction,
                          buttonSpacing = buttonSpacing,
                          modifier = Modifier
                              .fillMaxSize()
                              .background(Color.DarkGray)



                  )





                    }

                }
            }
        }

        @Preview(showBackground = true)
        @Composable
        fun CalculatorPreview(){
            val viewModel=viewModel<CalculatorViewModel>()
            val state=viewModel.state
            val buttonSpacing=8.dp
            Calculator(
                state=state,
                onAction=viewModel::onAction,
                buttonSpacing=buttonSpacing,
                modifier= Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .padding(16.dp)
            )


        }