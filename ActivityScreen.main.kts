package com.example.cp3406assessment3educationapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ActivityScreen(onNavigateBack: () -> Unit) {
    val sampleQuestion = "What comes next: 2, 4, 8, 16, __?"
    val sampleOptions = listOf("20", "24", "32", "64")
    val correctAnswer = "32"

    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }
    var timerSeconds by remember { mutableIntStateOf(30) }

    LaunchedEffect(key1 = timerSeconds, key2 = isAnswered) {
        if (timerSeconds > 0 && !isAnswered) {
            delay(1000L)
            timerSeconds--
        } else if (timerSeconds == 0) {
            isAnswered = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = if (timerSeconds > 0) "Time Remaining: $timerSeconds s" else "Time's Up!",
                style = MaterialTheme.typography.titleMedium,
                color = if (timerSeconds < 10) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { timerSeconds / 30f },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Card(modifier = Modifier.fillMaxWidth().weight(1f).padding(vertical = 24.dp)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Category: Sequence Completion", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = sampleQuestion, style = MaterialTheme.typography.headlineMedium)
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            sampleOptions.forEach { option ->
                val isSelected = selectedOption == option
                val buttonColor = when {
                    isAnswered && option == correctAnswer -> Color(0xFF4CAF50)
                    isAnswered && isSelected && option != correctAnswer -> Color(0xFFF44336)
                    isSelected -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.surface
                }

                OutlinedButton(
                    onClick = { if (!isAnswered) selectedOption = option },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = buttonColor),
                    enabled = !isAnswered
                ) {
                    Text(text = option, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!isAnswered) {
                Button(
                    onClick = { isAnswered = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedOption != null
                ) {
                    Text("Submit Answer")
                }
            } else {
                Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) {
                    Text("Return to Hub")
                }
            }
        }
    }
}