package com.fuwafuwa.tip_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fuwafuwa.tip_calculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipTimeScreen()
                }
            }
        }
    }
}

@Composable
fun TipTimeScreen() {

    var amountInput by remember {mutableStateOf("")}
    //null이면 0.0
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    //tip계산
    val tip = calculateTip(amount)

    Column(modifier=Modifier.padding(32.dp),
        //하위 요소 사이에 고정된 8dp 공백이 추가.
    verticalArrangement = Arrangement.spacedBy(8.dp)){

        Text(text = stringResource(id = R.string.calculate_tip),
        fontSize = 24.sp,
        modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        EditNumberField(amountInput,{amountInput = it})

        Spacer(Modifier.height(24.dp))

        Text(
            //%s에 tip을 넣음.
            text = stringResource(R.string.tip_amount, tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun EditNumberField(value:String, onValueChange:(String)->Unit) {
    //앱에 텍스트 입력
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(stringResource(id = R.string.cost_of_service))},
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        //화면에 표시되는 키보드를 구성하여 숫자, 이메일 주소, URL, 비밀번호 등을 입력할 수 있는 옵션이 있다.
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)


    )
}

//퍼센트는 하드코딩
private fun calculateTip(amount:Double,tipPercent:Double = 15.0): String{

    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipTimeScreen()
    }
}