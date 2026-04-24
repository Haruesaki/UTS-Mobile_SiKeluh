package com.example.sikeluh

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sikeluh.ui.theme.SiKeluhTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatAduanScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Aduan", fontWeight = FontWeight.Bold, color = Color(0xFF198786)) },
                navigationIcon = { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
            )
        },
        bottomBar = { BottomNavigationBar() } // Menggunakan komponen dari file sebelumnya
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Text
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Riwayat Aduan Saya", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Pantau status laporan dan aduan yang telah Anda kirimkan kepada pemerintah kota.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            // Filter Section
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Search Bar
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            placeholder = { Text("Cari ID atau judul aduan...", color = Color.Gray, fontSize = 14.sp) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        // Status Dropdown
                        OutlinedTextField(
                            value = "Semua Status",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = Color.Gray) },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        // Sort Dropdown
                        OutlinedTextField(
                            value = "Terbaru",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { Icon(Icons.Default.Menu, contentDescription = "Sort", tint = Color.Gray) }, // Menggunakan Menu sebagai icon sort sementara
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray
                            )
                        )
                    }
                }
            }

            // List of Aduan Cards
            item {
                AduanItemCard(
                    statusText = "Dalam Proses",
                    statusColor = Color(0xFF1E3A8A), // Biru tua
                    statusBgColor = Color(0xFFDBEAFE), // Biru muda
                    statusIcon = null, // Hanya titik
                    idAduan = "#ADU-2023-089",
                    title = "Jalan Rusak dan Berlubang di Jl. Merdeka Selatan",
                    description = "Terdapat lubang besar yang membahayakan pengendara motor, terutama saat malam hari...",
                    date = "12 Okt 2023"
                )
            }

            item {
                AduanItemCard(
                    statusText = "Selesai",
                    statusColor = Color(0xFF065F46), // Hijau tua
                    statusBgColor = Color(0xFF6EE7B7), // Hijau muda/hijau terang
                    statusIcon = Icons.Default.CheckCircle,
                    idAduan = "#ADU-2023-042",
                    title = "Lampu Jalan Mati di Taman Suropati",
                    description = "Sudah seminggu lampu taman bagian utara mati, mohon segera diperbaiki untuk...",
                    date = "05 Sep 2023"
                )
            }

            item {
                AduanItemCard(
                    statusText = "Menunggu Verifikasi",
                    statusColor = Color(0xFF991B1B), // Merah tua
                    statusBgColor = Color(0xFFFEE2E2), // Merah muda/pink
                    statusIcon = Icons.Default.Assignment,
                    idAduan = "#ADU-2023-102",
                    title = "Tumpukan Sampah Liar di Pasar Induk",
                    description = "Bau menyengat dan mengganggu aktivitas warga sekitar karena sampah menumpuk...",
                    date = "24 Okt 2023"
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp)) // Spacer bawah
            }
        }
    }
}

@Composable
fun AduanItemCard(
    statusText: String,
    statusColor: Color,
    statusBgColor: Color,
    statusIcon: ImageVector?,
    idAduan: String,
    title: String,
    description: String,
    date: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Card: Status dan ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status Chip
                Row(
                    modifier = Modifier
                        .background(statusBgColor, RoundedCornerShape(16.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (statusIcon != null) {
                        Icon(statusIcon, contentDescription = null, tint = statusColor, modifier = Modifier.size(12.dp))
                    } else {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(statusColor))
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(statusText, color = statusColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                // ID Aduan
                Text(idAduan, color = Color.Gray, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Judul dan Deskripsi
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                description,
                color = Color.DarkGray,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Footer Card: Tanggal dan Tombol Detail
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, contentDescription = "Date", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(date, color = Color.DarkGray, fontSize = 14.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Detail", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = "Detail", modifier = Modifier.size(16.dp), tint = Color(0xFF0F172A))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AduanPreview() {
    SiKeluhTheme {
        RiwayatAduanScreen()
    }
}