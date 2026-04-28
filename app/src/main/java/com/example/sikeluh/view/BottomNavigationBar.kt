package com.example.sikeluh.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.ui.theme.SiKeluhTheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    // Mendapatkan rute aktif saat ini
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF2D4653)
    ) {
        val navItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.Gray, // Warna lebih pudar jika tidak aktif
            unselectedTextColor = Color.Gray,
            // Memberikan warna frame (pill background) saat tab terpilih
            indicatorColor = Color(0xFF1CB58F)
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.navigate("home") {
                        // Menghindari penumpukan layar (stack)
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = navItemColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Aduan Saya") },
            label = { Text("Aduan Saya") },
            selected = currentRoute == "riwayat",
            onClick = {
                if (currentRoute != "riwayat") {
                    navController.navigate("riwayat") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = navItemColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notification") },
            label = { Text("Notification") },
            selected = currentRoute == "notif",
            onClick = { /* TODO */ },
            colors = navItemColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "profile",
            onClick = { /* TODO */ },
            colors = navItemColors
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavigationBarPreview() {
    BottomNavigationBar(rememberNavController())
}