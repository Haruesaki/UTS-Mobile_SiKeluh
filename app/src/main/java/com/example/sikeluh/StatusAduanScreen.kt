package com.example.sikeluh

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusAduanScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Status Aduan", fontWeight = FontWeight.Bold, color = Color(0xFF198786)) },
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
            Text("Jalan Rusak di Jl. Merdeka No.123, Kecamatan Ialaul", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Diadukan pada 25 November 2025", color = Color.Gray)

            // Catatan Instansi
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF2D4653))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Catatan Instansi", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(Color(0xFFF5F5F5))
                            .border(1.dp, Color.LightGray)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text("Dinas Terkait • 14 Okt 2023, 10:30 AM", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Tim lapangan telah dikerahkan ke lokasi untuk melakukan pengecekan awal...", fontSize = 14.sp)
                        }
                    }
                }
            }

            // Status Laporan (Timeline)
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Status Laporan", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    TimelineItem("Laporan Terkirim", "12 Okt 2023, 09:45 AM", true)
                    TimelineItem("Diverifikasi", "13 Okt 2023, 14:20 PM", true)
                    TimelineItem("Dalam Pengerjaan", "14 Okt 2023, 08:00 AM", true, isCurrent = true)
                    TimelineItem("Selesai", "Menunggu penyelesaian", false, isLast = true)
                }
            }

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1CB58F))
            ) {
                Icon(Icons.Default.Phone, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Hubungi Layanan Bantuan", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun TimelineItem(title: String, time: String, isDone: Boolean, isCurrent: Boolean = false, isLast: Boolean = false) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isDone) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF0F172A))
            } else {
                Box(modifier = Modifier.size(24.dp).border(2.dp, Color.Gray, CircleShape))
            }
            if (!isLast) {
                Box(modifier = Modifier.width(2.dp).fillMaxHeight().background(if (isDone) Color(0xFF0F172A) else Color.LightGray))
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            Text(title, fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal, color = if (isDone || isCurrent) Color.Black else Color.Gray)
            Text(time, fontSize = 12.sp, color = Color.Gray)
        }
    }
}