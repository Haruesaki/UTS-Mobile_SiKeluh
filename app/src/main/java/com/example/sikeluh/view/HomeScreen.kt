package com.example.sikeluh.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sikeluh.R

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pp),
                        contentDescription = "Foto Profil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Text("Si Keluh", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Row {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            }

            item {
                Text("Hallo Doni 👋", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Sampaikan keluhanmu,\nkami teruskan ke Pemerintah Daerah", color = Color.Gray)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2D4653)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Keluhan Anda,\nTugas Kami,\nTindak Lanjut Mereka",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = { navController.navigate("form") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1CB58F))
                            ) {
                                Text("Buat Aduan Baru", fontSize = 12.sp)
                                Icon(Icons.Default.ArrowForward, null, Modifier.padding(start = 4.dp).size(16.dp))
                            }
                        }
                        Image(
                            painter = painterResource(id = R.drawable.istana),
                            contentDescription = null,
                            modifier = Modifier.size(110.dp)
                        )
                    }
                }
            }

            item { SectionTitle(title = "Aduan Terbaru") }
            item {
                AduanCardPlaceholder("Jalan Rusak", "Dalam Proses", Color(0xFF81C784), R.drawable.jalanrusak)
            }

            item { SectionTitle(title = "Jelajahi Aduan") }
            item {
                AduanCardPlaceholder("Jalan Rusak", "Selesai", Color(0xFFAED581), R.drawable.jalanbagus)
            }

            item { Text("Kategori Aduan", fontWeight = FontWeight.Bold, fontSize = 18.sp) }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    CategoryIcon(Icons.Default.Build, "Jalan &\nInfrastruktur")
                    CategoryIcon(Icons.Default.Warning, "Lalu Lintas")
                    CategoryIcon(Icons.Default.Delete, "Sampah &\nKebersihan")
                    CategoryIcon(Icons.Default.WaterDrop, "Air Bersih")
                    CategoryIcon(Icons.Default.GridView, "Lainnya")
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("Lihat Semua ->", fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun AduanCardPlaceholder(title: String, status: String, statusColor: Color, imageResId: Int) {
    Card(modifier = Modifier.fillMaxWidth().height(100.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF2D4653))) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(76.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Card(colors = CardDefaults.cardColors(containerColor = statusColor)) {
                    Text(status, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 10.sp, color = Color.DarkGray)
                }
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                Text("Jl. Merdeka No.123...", color = Color.LightGray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CategoryIcon(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(60.dp).background(Color(0xFF2D4653), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = Color.White)
        }
        Text(label, fontSize = 10.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 4.dp))
    }
}