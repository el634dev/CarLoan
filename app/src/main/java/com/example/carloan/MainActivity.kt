package com.example.carloan

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carloan.ui.theme.CarLoanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarLoanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CarLoanScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
// -------------------------------------------------------------------
@Composable
fun CarLoanScreen(modifier: Modifier = Modifier) {
    var loanNum by remember { mutableStateOf("") }
    var downPayment by remember { mutableStateOf("") }
    var interestRate by remember { mutableFloatStateOf(0.00f) }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.padding(10.dp)
    ) {
        Text(
            text = "Car Loan Calculator",
            fontSize = 25.sp,
            modifier = modifier.padding( 10.dp )
        )
// -------------------------------------------------------------------
// ---------------------*---- CAR PURCHASE FIELD ----*----------------
        TextField(
            value = loanNum,
            singleLine = true,
            label = {
                Text(text = "Car Purchase Price: ")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = {
                loanNum = it
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
// -------------------------------------------------------------------
// ---------------------*---- DOWN PAYMENT FIELD ----*----------------
        TextField(
            value = downPayment,
            singleLine = true,
            label = {
                Text(text = "Down Payment Amount: ")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = {
                downPayment = it
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        RadioGroup(
            radioOptions = listOf(10, 15, 20, 30),
        )
        InterestSlider( interestVal = interestRate, onChange = { interestRate = it } )
// -----------------------------------------------------------------
//      ----------------*---- CALCULATE BUTTON ----*----------------
        Button(
//          Here is where to do the calculations
            onClick = {},
            modifier = modifier
        ) {
            Text("Calculate")
        }
    }
}

// -----------------------------------------------------------
// ----------------*---- SLIDER FUNCTION ----*----------------
@Composable
fun InterestSlider(interestVal: Float, onChange: (Float)->Unit) {
    // Slider to adjust the annual interest rate
    Slider(
        value = interestVal,
        onValueChange = {
            onChange(it)
        },
        valueRange = 0f .. 30f,
        modifier = Modifier.padding(8.dp)
    )
    Text(
        text = interestVal.toInt().toString(),
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier.fillMaxWidth()
    )
}

// ----------------------------------------------------------------
// ----------------*---- RADIO GROUP FUNCTION ----*----------------
@Composable
fun RadioGroup(radioOptions: List<Int>){
    // mutableIntStateOf
    var selectedOption by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
    ) {
        Text(
            text = "Length of the Loan in Years",
            fontSize = 25.sp,
            modifier = Modifier.padding(10.dp )
        )
        // Gives a list of radio buttons
        radioOptions.forEach{ option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = option.toString()
                )
            }
        }
    }
}

// ----------------------------------------------------------------------
// ----------------*---- PREVIEW FUNCTION (OPTIONAL) ----*----------------
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarLoanTheme {
       CarLoanScreen()
    }
}