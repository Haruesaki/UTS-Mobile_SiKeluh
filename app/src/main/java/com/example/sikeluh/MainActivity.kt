package com.example.sikeluh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sikeluh.ui.theme.SiKeluhTheme
import com.example.sikeluh.ui.screens.*
import com.example.sikeluh.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SiKeluhTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

                // Wait until session check is complete
                if (isLoggedIn == null) {
                    // Show a simple loading screen or splash
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    val startDest = if (isLoggedIn == true) "home" else "welcome"

                    NavHost(navController = navController, startDestination = startDest) {
                        composable("welcome") { WelcomeScreen(navController) }
                        composable("login") { LoginScreen(navController, authViewModel) }
                        composable("register") { RegisterScreen(navController, authViewModel) }
                        
                        composable("home") { HomeScreen(navController) }
                        composable("form") { FormAduanScreen(navController) }
                        composable("riwayat") { RiwayatAduanScreen(navController) }
                        composable("notif") { NotifikasiScreen(navController) }
                        composable("profile") { ProfileScreen(navController, authViewModel) }
                        composable("map_selection") { MapSelectionScreen(navController) }

                        // Rute dengan parameter status
                        composable(
                            route = "status/{status}",
                            arguments = listOf(navArgument("status") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val status = backStackEntry.arguments?.getString("status") ?: "Menunggu Verifikasi"
                            StatusAduanScreen(navController, status)
                        }
                        // Fallback route tanpa parameter
                        composable("status") { StatusAduanScreen(navController, "Menunggu Verifikasi") }
                    }
                }
            }
        }
    }
}
