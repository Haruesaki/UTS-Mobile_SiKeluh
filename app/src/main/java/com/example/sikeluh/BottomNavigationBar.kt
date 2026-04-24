package com.example.sikeluh

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF2D4653) // Warna background gelap navbar
    ) {
        // Pengaturan warna agar teks dan ikon tetap putih rata
        val navItemColors = NavigationBarItemDefaults.colors(
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.White,
            indicatorColor = Color.Transparent // Memastikan tidak ada background pil
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false, // <-- Diubah menjadi false
            onClick = { /* TODO: Logika navigasi nanti */ },
            colors = navItemColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Aduan Saya") },
            label = { Text("Aduan Saya") },
            selected = false,
            onClick = { /* TODO: Logika navigasi nanti */ },
            colors = navItemColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notification") },
            label = { Text("Notification") },
            selected = false,
            onClick = { /* TODO: Logika navigasi nanti */ },
            colors = navItemColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* TODO: Logika navigasi nanti */ },
            colors = navItemColors
        )
    }
}

// Tambahan fungsi Preview agar Anda bisa melihatnya langsung di file ini
@Preview(showBackground = true)
@Composable
private fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}