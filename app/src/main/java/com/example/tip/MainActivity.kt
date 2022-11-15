package com.example.tip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tip.ui.theme.TipTheme
import java.text.NumberFormat

/*
* Name - Vinay Kumar Kharwar
* Github - https://www.github.com/onebitjoy
* Title - Tip Calculator
* Updated latest on - 15th November, 2022 / 19:01
* */
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
    var amountInput by remember { mutableStateOf("") } // Amount entered
    var tipInput by remember { mutableStateOf("") } // Tip percentage entered
    var checked by remember { mutableStateOf(false) } // Switch on/off


    // Variable: Watching Variable for the observables
    val amount = amountInput.toDoubleOrNull() ?: 0.0 // convert amountInput to Double and then calculate tip
    val tipvalue = tipInput.toDoubleOrNull() ?: 15.0 // Default Tip Percentage = 15%

    val tip: Double = tipCalc(amount, tipvalue, checked)
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFAEC))
            .padding(top = 100.dp)
    ) {
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


            NumberField(
                amountInput,
                onValueChanged = { amountInput = it },
                ImeAction.Next,
                "Amount",
                KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            Spacer(modifier = Modifier.height(30.dp))

            NumberField(
                tipInput,
                onValueChanged = { tipInput = it },
                ImeAction.Done,
                "Tip Percentage",
                KeyboardActions(onDone = { focusManager.clearFocus() })
            )
            Spacer(modifier = Modifier.height(70.dp))

            RoundTheTip(roundUp = checked, onRoundUpChanged = { checked = it })
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(
                    id = R.string.show,
                    NumberFormat.getCurrencyInstance().format(tip)
                ), fontSize = 36.sp
            )
        }
    }

}

// Editable Number Field
@Composable
fun NumberField(
    amountInput: String,
    onValueChanged: (String) -> Unit,
    imicon: ImeAction = ImeAction.Next,
    label: String,
    keyboardActions: KeyboardActions
) {
    Card(
        elevation = 5.dp, backgroundColor = Color(0xFFFCF6D8)
    ) {
        TextField(
            placeholder = { Text(text = label, fontSize = 24.sp, color = Color(0xFF656865)) },
            value = amountInput,
            onValueChange = onValueChanged,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            textStyle = MaterialTheme.typography.h3,
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, imeAction = imicon
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

// Switch Function
@Composable
fun RoundTheTip(roundUp: Boolean, onRoundUpChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = "Round Off?", fontSize = 24.sp)
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = Modifier
                .wrapContentWidth(Alignment.End)
                .padding(start = 5.dp),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color(0xFF59C1F1),
                uncheckedTrackColor = Color(0xFFABE4EE),
                checkedTrackColor = Color(0xFFF52727),
                checkedThumbColor = Color(0xFFF80026),
            )
        )
    }
}

//Tip Function
internal fun tipCalc(amount: Double, tipvalue: Double = 15.0, roundOff: Boolean = false): Double {
    var tipped = tipvalue / 100 * amount
    if (roundOff)
        tipped = kotlin.math.ceil(tipped)
    return tipped
}

//Committed










