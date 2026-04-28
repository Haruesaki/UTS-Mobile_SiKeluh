package com.example.sikeluh.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.ui.theme.SiKeluhTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusAduanScreen(navController: NavController, status: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Status Aduan", fontWeight = FontWeight.Bold, color = Color(0xFF198786)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF198786))
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Jalan Rusak di Jl. Merdeka No.123, Kecamatan lalauI",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp
                )
                Text(
                    text = "Diadukan pada 25 November 2025",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Catatan Instansi Section
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = Color(0xFF1E3A8A), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Catatan Instansi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Dinas Terkait", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(" • 14 Okt 2023, 10:30 AM", color = Color.Gray, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Tim lapangan telah dikerahkan ke lokasi untuk melakukan pengecekan awal dan mempersiapkan alat berat. Estimasi pengerjaan akan memakan waktu kurang lebih 3 hari kerja. Mohon maaf atas ketidaknyamanan yang terjadi.",
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }

            // Status Laporan Section
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Status Laporan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color(0xFFE2E8F0))
                        Spacer(modifier = Modifier.height(20.dp))

                        // Progress Logic
                        val currentStep = when (status) {
                            "Menunggu Verifikasi" -> 2 // Sedang Diverifikasi
                            "Dalam Proses" -> 3       // Sedang Dalam Pengerjaan
                            "Selesai" -> 4            // Sudah Selesai
                            else -> 1
                        }

                        StatusStepItem(
                            title = "Laporan Terkirim",
                            time = "12 Okt 2023, 09:45 AM",
                            isCompleted = currentStep > 1,
                            isActive = currentStep == 1,
                            showLine = true
                        )
                        StatusStepItem(
                            title = "Diverifikasi",
                            time = if (currentStep >= 2) "13 Okt 2023, 14:20 PM" else "Menunggu verifikasi",
                            isCompleted = currentStep > 2,
                            isActive = currentStep == 2,
                            showLine = true
                        )
                        StatusStepItem(
                            title = "Dalam Pengerjaan",
                            time = if (currentStep >= 3) "14 Okt 2023, 08:00 AM" else "Menunggu pengerjaan",
                            isCompleted = currentStep > 3,
                            isActive = currentStep == 3,
                            showLine = true
                        )
                        StatusStepItem(
                            title = "Selesai",
                            time = if (currentStep >= 4) "15 Okt 2023, 16:00 PM" else "Menunggu penyelesaian",
                            isCompleted = currentStep > 4,
                            isActive = currentStep == 4,
                            showLine = false
                        )
                    }
                }
            }

            // Button Hubungi Layanan
            item {
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF4ADE80), Color(0xFF0D9488))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Hubungi Layanan Bantuan", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StatusStepItem(
    title: String,
    time: String,
    isCompleted: Boolean,
    isActive: Boolean,
    showLine: Boolean
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isCompleted -> Color(0xFF0F172A)
                            isActive -> Color.White
                            else -> Color.White
                        }
                    )
                    .then(
                        if (isActive) Modifier.background(Color.White).padding(2.dp).clip(CircleShape).background(Color(0xFF0F172A)).padding(2.dp).clip(CircleShape).background(Color.White)
                        else if (!isCompleted) Modifier.background(Color.White).padding(2.dp).clip(CircleShape).background(Color.LightGray).padding(2.dp).clip(CircleShape).background(Color.White)
                        else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                } else if (isActive) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF0F172A)))
                }
            }
            if (showLine) {
                Canvas(modifier = Modifier.height(40.dp).width(2.dp)) {
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (isCompleted || isActive) Color(0xFF0F172A) else Color.Gray
            )
            Text(
                text = time,
                fontSize = 13.sp,
                color = if (isCompleted || isActive) Color.DarkGray else Color.LightGray
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatusAduanScreenPreview() {
    SiKeluhTheme {
        StatusAduanScreen(navController = rememberNavController(), status = "Dalam Proses")
    }
}
