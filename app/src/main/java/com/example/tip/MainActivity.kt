package com.example.tip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tip.ui.theme.TipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTheme() {
                MainScreen()
            }
        }
    }

}

// Preview Function
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipPreview() {
    TipTheme {
        MainScreen()
    }
}

// -----------------------------------
// Main Screen Composable
@Composable
fun MainScreen() {
    // User Input Changing Values: Remember by observables
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }

    // Variable: Watching Variable for the observables
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipvalue = tipInput.toDoubleOrNull() ?: 15.0 // Default Tip Percentage = 15%

    val tip: Double = tipCalc(amount, tipvalue)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFABC3E7))
        .padding(top = 100.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tip Calculator",
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(top = 20.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))


            NumberField(amountInput, onValueChanged = { amountInput = it }, ImeAction.Next, "Amount")
            Spacer(modifier = Modifier.height(15.dp))

            NumberField(tipInput, onValueChanged = { tipInput = it }, ImeAction.Done, "Tip Percentage")
            Spacer(modifier = Modifier.height(70.dp))

            Text(text = stringResource(id = R.string.show, tip), fontSize = 36.sp)
        }
    }

}

// Editable Number Field
@Composable
fun NumberField(
    amountInput: String,
    onValueChanged: (String) -> Unit,
    imicon: ImeAction = ImeAction.Next,
    label:String
) {
    Card(
        elevation = 5.dp,
        backgroundColor = Color(0xFFD0E6F0)
    ) {
        TextField(
            placeholder = {Text(text = label, fontSize = 24.sp, color = Color(0xFF656865))},
            value = amountInput,
            onValueChange = onValueChanged,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            textStyle = MaterialTheme.typography.h3,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = imicon
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color(0xFF99F0F0),
                focusedIndicatorColor = Color(0xFF4FB2E7),
                unfocusedIndicatorColor = Color(0xFFF55176),
            )
        )
    }

}

//Tip Function
internal fun tipCalc(amount: Double, tipvalue: Double = 15.0): Double {
    return (tipvalue * amount) / 100
}











