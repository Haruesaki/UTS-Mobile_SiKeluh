package com.example.sikeluh.ui.components // Sesuaikan dengan package Anda

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF2D4653), // Warna background gelap navbar
        contentColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
            label = { Text("Home", color = Color.White) },
            selected = true, // Logika navigasi sesungguhnya bisa ditambahkan nanti
            onClick = { /* TODO: Navigasi ke Home */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Aduan Saya", tint = Color.White) },
            label = { Text("Aduan Saya", color = Color.White) },
            selected = false,
            onClick = { /* TODO: Navigasi ke Aduan Saya */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notification", tint = Color.White) },
            label = { Text("Notification", color = Color.White) },
            selected = false,
            onClick = { /* TODO: Navigasi ke Notifikasi */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White) },
            label = { Text("Profile", color = Color.White) },
            selected = false,
            onClick = { /* TODO: Navigasi ke Profile */ }
        )
    }
}