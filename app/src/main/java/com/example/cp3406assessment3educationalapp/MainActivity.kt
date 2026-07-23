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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
        composable(MindMazeScreen.Settings.route) {
            SettingsScreen(
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
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to the MindMaze!",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = onNavigateToActivity, modifier = Modifier.padding(top = 16.dp)) {
            Text("Continue / Start Puzzles")
        }
        Button(onClick = onNavigateToStats, modifier = Modifier.padding(top = 8.dp)) {
            Text("View Stats")
        }
        Button(onClick = onNavigateToSettings, modifier = Modifier.padding(top = 8.dp)) {
            Text("Settings & Transparency")
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Total Solved", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "42", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Accuracy Rate", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "85%", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF4CAF50))
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Weekly Activity (Puzzles Finished)", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val days = listOf("M" to 0.3f, "T" to 0.6f, "W" to 0.9f, "T" to 0.5f, "F" to 0.8f, "S" to 0.2f, "S" to 0.4f)
                    days.forEach { (day, targetFill) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Card(
                                modifier = Modifier
                                    .width(16.dp)
                                    .weight(1f, fill = false)
                                    .height((100 * targetFill).dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {}
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = day, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Cognitive Focus Strengths", style = MaterialTheme.typography.titleSmall)

                Column {
                    Text(text = "Logic & Analysis (92%)", style = MaterialTheme.typography.bodySmall)
                    LinearProgressIndicator(progress = { 0.92f }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text(text = "Attention & Focus (78%)", style = MaterialTheme.typography.bodySmall)
                    LinearProgressIndicator(progress = { 0.78f }, modifier = Modifier.fillMaxWidth())
                }
            }
        }

        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Return to Hub")
        }
    }
}

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var analyticsEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            Text(
                text = "Control Hub ⚙️",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Ethics-by-Design & Personalization Settings",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Daily Training Reminders", style = MaterialTheme.typography.titleSmall)
                            Text(text = "Receive a nudging notification to keep up your daily streak.", style = MaterialTheme.typography.bodySmall)
                        }
                        // Simple custom check layout for visual feedback
                        OutlinedButton(
                            onClick = { notificationsEnabled = !notificationsEnabled },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (notificationsEnabled) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                            )
                        ) {
                            Text(if (notificationsEnabled) "ON" else "OFF")
                        }
                    }
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Privacy: Anonymous Metrics", style = MaterialTheme.typography.titleSmall)
                            Text(text = "Share completely anonymized task latency to help adjust puzzle difficulty algorithms.", style = MaterialTheme.typography.bodySmall)
                        }
                        OutlinedButton(
                            onClick = { analyticsEnabled = !analyticsEnabled },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (analyticsEnabled) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                            )
                        ) {
                            Text(if (analyticsEnabled) "ON" else "OFF")
                        }
                    }
                }
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Data Sovereignty: You completely own your learning statistics profile data. Deleting your profile wipes all local score arrays permanently.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedButton(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Purge Local Progress History Data")
            }

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save & Return to Hub")
            }
        }
    }
}