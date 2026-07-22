package com.example.cp3406assessment3educationalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cp3406assessment3educationalapp.ui.theme.CP3406Assessment3EducationAppTheme
import kotlinx.coroutines.delay

sealed class MindMazeScreen(val route: String) {
    object Landing : MindMazeScreen("landing")
    object Activity : MindMazeScreen("activity")
    object Stats : MindMazeScreen("stats")
    object Settings : MindMazeScreen("settings")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CP3406Assessment3EducationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MindMazeApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MindMazeApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MindMazeScreen.Landing.route,
        modifier = modifier
    ) {
        composable(MindMazeScreen.Landing.route) {
            LandingScreen(
                onNavigateToActivity = { navController.navigate(MindMazeScreen.Activity.route) },
                onNavigateToStats = { navController.navigate(MindMazeScreen.Stats.route) },
                onNavigateToSettings = { navController.navigate(MindMazeScreen.Settings.route) }
            )
        }
        composable(MindMazeScreen.Activity.route) {
            ActivityScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(MindMazeScreen.Stats.route) {
            StatsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(MindMazeScreen.Settings.route) {
            MenuScreen(title = "Settings Screen (Ethics-by-Design)")
        }
    }
}

@Composable
fun LandingScreen(
    onNavigateToActivity: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentStreak = 3
    val dailyPuzzlePreview = "What comes next: 2, 4, 8, 16, __?"

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        // 1. Greeting Layout
        Column(modifier = Modifier.padding(top = 16.dp)) {
            Text(
                text = "Hello, Mind Racer! 🧠",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Ready to flex your logic muscles today?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Streak",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "$currentStreak Day Streak!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Consistency builds sharp minds. Keep it up!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Daily Puzzle"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Puzzle of the Day",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = dailyPuzzlePreview,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onNavigateToActivity,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Continue Training")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateToStats,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("View Stats")
                }
                OutlinedButton(
                    onClick = onNavigateToSettings,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Settings")
                }
            }
        }
    }
}

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

@Composable
fun MenuScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}
@Composable
fun StatsScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {

        Column(modifier = Modifier.padding(top = 16.dp)) {
            Text(
                text = "Performance Hub 📊",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Track your cognitive growth over time.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        }
    }
}