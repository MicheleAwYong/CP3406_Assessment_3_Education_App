package com.example.cp3406assessment3educationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cp3406assessment3educationapp.ui.theme.CP3406Assessment3EducationAppTheme

// Renamed to prevent conflicting overloads with other classes named "Screen"
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
            MenuScreen(title = "Activity Screen (Puzzles)")
        }
        composable(MindMazeScreen.Stats.route) {
            MenuScreen(title = "Stats Screen (Analytics)")
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
fun MenuScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}
