package com.example.sikeluh.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAduanScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Form Aduan", style = MaterialTheme.typography.titleLarge, color = PrimaryTeal) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Aduan Keluhan", style = MaterialTheme.typography.displaySmall)
            Text("Sampaikan Aduan Anda dengan detail yang jelas...", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Kategori Keluhan", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Pilih Kategori", style = MaterialTheme.typography.bodyMedium) }, modifier = Modifier.fillMaxWidth())
                    Text("Deskripsi Keluhan", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Jelaskan secara detail...", style = MaterialTheme.typography.bodyMedium) }, modifier = Modifier.fillMaxWidth().height(100.dp))
                    Text("Lampiran Foto", style = MaterialTheme.typography.titleMedium)
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.dp).border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CameraAlt, null, tint = Color.Gray)
                            Text("Unggah Foto", color = Color.Gray, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Lokasi Aduan", style = MaterialTheme.typography.titleMedium)
                    Box(modifier = Modifier.fillMaxWidth().height(150.dp).border(1.dp, Color.Gray, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)) {
                            Icon(Icons.Default.LocationOn, null)
                            Text("Pilih Lokasi", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    Text("Provinsi, Kota, Kecamatan", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Lampung, Bandar Lampung...", style = MaterialTheme.typography.bodyMedium) }, modifier = Modifier.fillMaxWidth())
                }
            }

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentGreen)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kirim Aduan", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FormAduanPreview() {
    // Membuat NavController palsu untuk keperluan preview
    val navController = rememberNavController()

    SiKeluhTheme {
        FormAduanScreen(navController = navController)
    }
}
