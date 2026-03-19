package com.example.carloan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CarLoanView: ViewModel() {
    var purchasePrice by mutableStateOf("")
    var downPayment by mutableStateOf("")

    var interest by mutableFloatStateOf(5.0f)
    var loanAmount by mutableIntStateOf(10)
    var paymentTotal by mutableDoubleStateOf(0.0)
}