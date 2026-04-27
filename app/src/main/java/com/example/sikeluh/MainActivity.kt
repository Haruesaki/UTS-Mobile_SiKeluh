package com.example.sikeluh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.ui.theme.SiKeluhTheme
import com.example.sikeluh.view.FormAduanScreen
import com.example.sikeluh.view.HomeScreen
import com.example.sikeluh.view.RiwayatAduanScreen
import com.example.sikeluh.view.StatusAduanScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SiKeluhTheme {
                // Inisialisasi NavController
                val navController = rememberNavController()

                // Daftarkan semua route di sini dan passing navController
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController) }
                    composable("form") { FormAduanScreen(navController) }
                    composable("riwayat") { RiwayatAduanScreen(navController) }
                    composable("status") { StatusAduanScreen(navController) }
                }
            }
        }
    }
}