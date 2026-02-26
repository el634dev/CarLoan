package com.example.carloan

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carloan.ui.theme.CarLoanTheme
import kotlin.math.pow

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
    var purchasePrice by remember { mutableStateOf("") }
    var downPayment by remember { mutableStateOf("") }

    var interest by remember { mutableFloatStateOf(5.0f) }
    var loanAmount by remember { mutableIntStateOf(10) }
    var paymentTotal by remember { mutableDoubleStateOf(0.0) }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(start = 10.dp)
    ) {
        Text(
            text = "Car Loan Calculator",
            fontSize = 25.sp,
            modifier = modifier.padding( end = 10.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.car_25),
            contentDescription = "SUV with desert in the background",
            modifier = Modifier.size(200.dp, 200.dp)
        )
// -------------------------------------------------------------------
// ---------------------*---- CAR PURCHASE FIELD ----*----------------
        TextField(
            value = purchasePrice,
            singleLine = true,
            label = {
                Text( text = "Car Purchase Price: ")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = {
                purchasePrice = it
            },
            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
        )
// -------------------------------------------------------------------
// ---------------------*---- DOWN PAYMENT FIELD ----*----------------
        TextField(
            value = downPayment,
            singleLine = true,
            label = {
                Text( text = "Down Payment Amount: ")
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
        AnnualInterestSlider( interestVal = interest, onChange = { interest = it } )
        Text(
            text = String.format("Monthly Payment: %.2f", paymentTotal)
        )
// -----------------------------------------------------------------
// ---------------------*---- CALCULATE BUTTON ----*----------------
        Button(
            onClick = {
                paymentTotal = loanTotal(
                    downPayment = downPayment.toDouble(),
                    loanLength  = loanAmount,
                    annualInterest = interest,
                    purchasePrice = purchasePrice.toDouble()
                )
            },
            modifier = modifier
        ) {
            Text( text = "Calculate: ")
        }
    }
}

// ------------------------------------------------------------------
// ----------------*---- INTEREST RATE FUNCTION ----*----------------
fun loanTotal(annualInterest: Float, downPayment: Double, purchasePrice: Double, loanLength: Int): Double {
    val totalLoanAmount = purchasePrice - downPayment
    val monthlyInterest = (annualInterest / 100) / 12
    val totalPayments = loanLength * 12

    if (monthlyInterest == 0f) {
        return totalLoanAmount / totalPayments
    }

    val numerator = monthlyInterest * (1 + monthlyInterest).pow(totalPayments)
    val denominator = (1 + monthlyInterest).pow(totalPayments) - 1

    return totalLoanAmount * numerator / denominator
}

// --------------------------------------------------------------------------------
// ----------------*---- ANNUAL INTEREST RATE SLIDER FUNCTION ----*----------------
@Composable
fun AnnualInterestSlider(interestVal: Float, onChange: (Float)->Unit) {
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