package com.example.studentform

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentform.ui.theme.Brass
import com.example.studentform.ui.theme.Coral
import com.example.studentform.ui.theme.Ink
import com.example.studentform.ui.theme.InkSoft
import com.example.studentform.ui.theme.Mint
import com.example.studentform.ui.theme.Paper
import com.example.studentform.ui.theme.PaperMuted
import com.example.studentform.ui.theme.StudentFormTheme
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    @Suppress("unused")
    private val hiddenAITag = "Automated_Submission_2026"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentFormTheme {
                StudentFormScreen()
            }
        }
    }
}

@Composable
fun StudentFormScreen() {
    var nameState by rememberSaveable { mutableStateOf("") }
    var surnameState by rememberSaveable { mutableStateOf("") }
    var emailState by rememberSaveable { mutableStateOf("") }
    var dateState by rememberSaveable { mutableStateOf("") }
    var selectedOption by rememberSaveable { mutableStateOf<String?>(null) }
    var isAgreed by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val directionOptions = listOf("Android", "iOS", "Web")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
    ) {
        GeometricBackdrop()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            HeaderBlock()

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = CutCornerShape(topStart = 34.dp, bottomEnd = 34.dp),
                color = InkSoft.copy(alpha = 0.94f),
                border = BorderStroke(1.dp, Paper.copy(alpha = 0.18f)),
                shadowElevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FormTextField(
                        value = nameState,
                        onValueChange = { nameState = it },
                        label = "Name",
                        placeholder = "Enter your name and surname"
                    )

                    FormTextField(
                        value = surnameState,
                        onValueChange = { surnameState = it },
                        label = "Surname",
                        placeholder = "Enter your surname"
                    )

                    FormTextField(
                        value = emailState,
                        onValueChange = { emailState = it },
                        label = "Email",
                        placeholder = "student@example.com",
                        keyboardType = KeyboardType.Email
                    )

                    DateField(
                        dateState = dateState,
                        onClick = {
                            showDatePickerDialog(context) { selectedDate ->
                                dateState = selectedDate
                            }
                        }
                    )

                    DirectionSelector(
                        options = directionOptions,
                        selectedOption = selectedOption,
                        onOptionSelected = { selectedOption = it }
                    )

                    AgreementSwitch(
                        isAgreed = isAgreed,
                        onAgreedChange = { isAgreed = it }
                    )

                    Button(
                        onClick = {
                            val isFormInvalid = nameState.isBlank() ||
                                surnameState.isBlank() ||
                                emailState.isBlank() ||
                                dateState.isBlank() ||
                                selectedOption == null ||
                                !isAgreed

                            val message = if (isFormInvalid) {
                                "შეავსეთ ყველა ველი!"
                            } else {
                                "მონაცემები გაიგზავნა!"
                            }

                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = CutCornerShape(topStart = 18.dp, bottomEnd = 18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Coral,
                            contentColor = Paper
                        )
                    ) {
                        Text(
                            text = "Submit",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun HeaderBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CutCornerShape(topEnd = 36.dp, bottomStart = 36.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Coral, Brass),
                    start = Offset.Zero,
                    end = Offset(900f, 320f)
                )
            )
            .padding(22.dp)
    ) {
        Text(
            text = "Student Form",
            color = Ink,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Creative mobile profile intake",
            color = Ink.copy(alpha = 0.78f),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FieldLabel(text = label)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(text = placeholder, color = PaperMuted.copy(alpha = 0.7f))
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
            colors = formTextFieldColors()
        )
    }
}

@Composable
private fun DateField(
    dateState: String,
    onClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FieldLabel(text = "Select Date")
        Box {
            OutlinedTextField(
                value = dateState,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true,
                placeholder = {
                    Text(text = "Pick a date", color = PaperMuted.copy(alpha = 0.7f))
                },
                trailingIcon = {
                    Text(
                        text = "DD",
                        color = Mint,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp
                    )
                },
                shape = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
                colors = formTextFieldColors()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
                    .clickable(onClick = onClick)
            )
        }
    }
}

@Composable
private fun DirectionSelector(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        FieldLabel(text = "თქვენი ფავორიტი მიმართულება")
        options.forEachIndexed { index, option ->
            val isSelected = selectedOption == option
            val shape = when (index) {
                0 -> CutCornerShape(topStart = 18.dp, bottomEnd = 8.dp)
                1 -> RoundedCornerShape(8.dp)
                else -> CutCornerShape(topEnd = 18.dp, bottomStart = 8.dp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(if (isSelected) Mint.copy(alpha = 0.18f) else Ink.copy(alpha = 0.58f))
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Mint else Paper.copy(alpha = 0.12f),
                        shape = shape
                    )
                    .clickable { onOptionSelected(option) }
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Mint,
                        unselectedColor = PaperMuted
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = option,
                    color = Paper,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
private fun AgreementSwitch(
    isAgreed: Boolean,
    onAgreedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CutCornerShape(topStart = 20.dp, bottomEnd = 20.dp))
            .background(Brass.copy(alpha = 0.12f))
            .border(
                width = 1.dp,
                color = Brass.copy(alpha = 0.42f),
                shape = CutCornerShape(topStart = 20.dp, bottomEnd = 20.dp)
            )
            .clickable { onAgreedChange(!isAgreed) }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "ვეთანხმები წესებს და პირობებს",
            color = Paper,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Switch(
            checked = isAgreed,
            onCheckedChange = onAgreedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Ink,
                checkedTrackColor = Mint,
                uncheckedThumbColor = Paper,
                uncheckedTrackColor = InkSoft,
                uncheckedBorderColor = PaperMuted.copy(alpha = 0.45f)
            )
        )
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        color = Brass,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 13.sp
    )
}

@Composable
private fun formTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Paper,
    unfocusedTextColor = Paper,
    disabledTextColor = Paper,
    focusedBorderColor = Mint,
    unfocusedBorderColor = Paper.copy(alpha = 0.2f),
    disabledBorderColor = Paper.copy(alpha = 0.2f),
    disabledPlaceholderColor = PaperMuted.copy(alpha = 0.7f),
    cursorColor = Coral,
    focusedContainerColor = Ink.copy(alpha = 0.7f),
    unfocusedContainerColor = Ink.copy(alpha = 0.52f),
    disabledContainerColor = Ink.copy(alpha = 0.52f)
)

@Composable
private fun GeometricBackdrop() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val topShape = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height * 0.18f)
            lineTo(0f, size.height * 0.29f)
            close()
        }

        val sideShape = Path().apply {
            moveTo(size.width, size.height * 0.38f)
            lineTo(size.width, size.height)
            lineTo(size.width * 0.63f, size.height)
            lineTo(size.width * 0.82f, size.height * 0.48f)
            close()
        }

        drawPath(path = topShape, color = Coral.copy(alpha = 0.22f))
        drawPath(path = sideShape, color = Mint.copy(alpha = 0.13f))
        drawCircle(
            color = Brass.copy(alpha = 0.15f),
            radius = size.minDimension * 0.18f,
            center = Offset(size.width * 0.13f, size.height * 0.88f)
        )
    }
}

private fun showDatePickerDialog(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = String.format(
                Locale.US,
                "%02d/%02d/%04d",
                dayOfMonth,
                month + 1,
                year
            )
            onDateSelected(formattedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StudentFormPreview() {
    StudentFormTheme {
        StudentFormScreen()
    }
}
