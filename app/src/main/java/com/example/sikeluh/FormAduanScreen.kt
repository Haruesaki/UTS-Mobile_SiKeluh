package com.example.sikeluh.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sikeluh.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAduanScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Form Aduan", fontWeight = FontWeight.Bold, color = Color(0xFF198786)) },
                navigationIcon = { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
            )
        },
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Aduan Keluhan", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Sampaikan Aduan Anda dengan detail yang jelas...", color = Color.Gray)

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Kategori Keluhan", fontWeight = FontWeight.Bold)
                    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Pilih Kategori") }, modifier = Modifier.fillMaxWidth())

                    Text("Deskripsi Keluhan", fontWeight = FontWeight.Bold)
                    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Jelaskan secara detail...") }, modifier = Modifier.fillMaxWidth().height(100.dp))

                    Text("Lampiran Foto", fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.dp).border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.Gray)
                            Text("Unggah Foto", color = Color.Gray)
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
                    Text("Lokasi Aduan", fontWeight = FontWeight.Bold)
                    // Placeholder Map
                    Box(modifier = Modifier.fillMaxWidth().height(150.dp).border(1.dp, Color.Gray, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)) {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                            Text("Pilih Lokasi")
                        }
                    }
                    Text("Provinsi, Kota, Kecamatan", fontWeight = FontWeight.Bold)
                    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Lampung, Bandar Lampung...") }, modifier = Modifier.fillMaxWidth())

                    Text("Nama Jalan atau Detail Lainnya", fontWeight = FontWeight.Bold)
                    OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
                }
            }

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1CB58F))
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kirim Aduan", fontSize = 16.sp)
            }
        }
    }
}