package com.example.sikeluh.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatAduanScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Aduan", fontWeight = FontWeight.Bold, color = PrimaryTeal) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Riwayat Aduan Saya", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Pantau status laporan yang telah Anda kirimkan.", color = Color.Gray, fontSize = 14.sp)
            }

            item {
                AduanItemCard(
                    navController = navController,
                    statusText = "Dalam Proses",
                    statusColor = StatusProsesText,
                    statusBgColor = StatusProsesBg,
                    idAduan = "#ADU-2023-089",
                    title = "Jalan Rusak di Jl. Merdeka Selatan",
                    description = "Terdapat lubang besar yang membahayakan pengendara motor, terutama saat malam hari...",
                    date = "12 Okt 2023"
                )
            }

            item {
                AduanItemCard(
                    navController = navController,
                    statusText = "Selesai",
                    statusColor = StatusSelesaiText,
                    statusBgColor = StatusSelesaiBg,
                    idAduan = "#ADU-2023-042",
                    title = "Lampu Jalan Mati",
                    description = "Lampu jalan di sekitar perumahan mati sudah 3 hari...",
                    date = "05 Sep 2023"
                )
            }

            item {
                AduanItemCard(
                    navController = navController,
                    statusText = "Menunggu Verifikasi",
                    statusColor = StatusMenungguText,
                    statusBgColor = StatusMenungguBg,
                    idAduan = "#ADU-2023-102",
                    title = "Tumpukan Sampah Liar",
                    description = "Bau menyengat dan mengganggu aktivitas warga sekitar...",
                    date = "24 Okt 2023"
                )
            }
        }
    }
}

@Composable
fun AduanItemCard(
    navController: NavController,
    statusText: String,
    statusColor: Color,
    statusBgColor: Color,
    idAduan: String,
    title: String,
    description: String,
    date: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navigasi ke rute status dengan parameter statusText
                navController.navigate("status/$statusText")
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .background(statusBgColor, RoundedCornerShape(16.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        statusText,
                        color = statusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(idAduan, color = Color.Gray, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Judul dan Deskripsi
            Text(
                title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                description,
                color = Color.DarkGray,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(date, color = Color.DarkGray, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RiwayatAduanScreenPreview() {
    val navController = rememberNavController()
    SiKeluhTheme {
        RiwayatAduanScreen(navController = navController)
    }
}
