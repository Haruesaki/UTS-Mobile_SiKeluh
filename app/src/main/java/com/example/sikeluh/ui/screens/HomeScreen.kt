package com.example.sikeluh.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sikeluh.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*
import com.example.sikeluh.viewmodel.AduanViewModel
import com.example.sikeluh.model.Aduan

@Composable
fun HomeScreen(navController: NavController, viewModel: AduanViewModel = viewModel()) {
    val aduans by viewModel.aduans.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAduans()
    }

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
                    Text("Si Keluh", style = MaterialTheme.typography.titleLarge)
                    Row {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            }

            item {
                Text("Hallo Doni 👋", style = MaterialTheme.typography.headlineMedium)
                Text("Sampaikan keluhanmu,\nkami teruskan ke Pemerintah Daerah", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryDark),
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
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = { navController.navigate("form") },
                                colors = ButtonDefaults.buttonColors(containerColor = AccentGreen)
                            ) {
                                Text("Buat Aduan Baru", style = MaterialTheme.typography.labelMedium)
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
            if (aduans.isEmpty()) {
                item {
                    Text(
                        "Belum ada aduan terbaru",
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            } else {
                val latestAduans = aduans.take(3)
                items(latestAduans) { aduan ->
                    AduanCardReal(aduan, navController)
                }
            }

            item { SectionTitle(title = "Jelajahi Aduan") }
            if (aduans.size > 3) {
                val moreAduans = aduans.drop(3)
                items(moreAduans) { aduan ->
                    AduanCardReal(aduan, navController)
                }
            } else {
                item {
                    AduanCardPlaceholder("Jalan Rusak", "Selesai", AduanSelesai, R.drawable.jalanbagus)
                }
            }

            item { Text("Kategori Aduan", style = MaterialTheme.typography.titleLarge) }
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
        Text(title, style = MaterialTheme.typography.titleLarge)
        Text("Lihat Semua ->", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
    }
}

@Composable
fun AduanCardReal(aduan: Aduan, navController: NavController) {
    val statusColor = when (aduan.status) {
        "Selesai" -> AduanSelesai
        else -> AduanProses
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { navController.navigate("status/${aduan.status}") }, 
        colors = CardDefaults.cardColors(containerColor = PrimaryDark)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Use placeholder image if no URL
            Image(
                painter = painterResource(id = R.drawable.jalanrusak),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(76.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Card(colors = CardDefaults.cardColors(containerColor = statusColor)) {
                    Text(
                        aduan.status ?: "Menunggu Verifikasi", 
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), 
                        style = MaterialTheme.typography.labelSmall, 
                        color = Color.DarkGray
                    )
                }
                Text(
                    aduan.kategoriKeluhan, 
                    color = Color.White, 
                    style = MaterialTheme.typography.titleMedium, 
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    aduan.lokasiAduan ?: "Lokasi tidak tersedia", 
                    color = Color.LightGray, 
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AduanCardPlaceholder(title: String, status: String, statusColor: Color, imageResId: Int) {
    Card(modifier = Modifier.fillMaxWidth().height(100.dp), colors = CardDefaults.cardColors(containerColor = PrimaryDark)) {
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
                    Text(status, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Color.DarkGray)
                }
                Text(title, color = Color.White, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 4.dp))
                Text("Jl. Merdeka No.123...", color = Color.LightGray, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun CategoryIcon(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(60.dp).background(PrimaryDark, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = Color.White)
        }
        Text(label, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 4.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    // Membuat NavController palsu untuk keperluan preview
    val navController = rememberNavController()

    SiKeluhTheme {
        HomeScreen(navController = navController)
    }
}
