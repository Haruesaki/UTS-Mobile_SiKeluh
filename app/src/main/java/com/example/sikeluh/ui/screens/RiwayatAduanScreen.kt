package com.example.sikeluh.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.model.Aduan
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*
import com.example.sikeluh.viewmodel.AduanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatAduanScreen(navController: NavController, viewModel: AduanViewModel = viewModel()) {
    val aduans by viewModel.aduans.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAduans()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Aduan", style = MaterialTheme.typography.titleLarge, color = PrimaryTeal) },
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
                Text("Riwayat Aduan Saya", style = MaterialTheme.typography.displaySmall)
                Text("Pantau status laporan yang telah Anda kirimkan.", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }

            if (aduans.isEmpty()) {
                item {
                    Text(
                        "Anda belum memiliki riwayat aduan",
                        modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            } else {
                items(aduans) { aduan ->
                    AduanItemCardReal(aduan, navController)
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun AduanItemCardReal(aduan: Aduan, navController: NavController) {
    val statusColor = when (aduan.status) {
        "Selesai" -> StatusSelesaiText
        "Dalam Proses" -> StatusProsesText
        else -> StatusMenungguText
    }
    
    val statusBgColor = when (aduan.status) {
        "Selesai" -> StatusSelesaiBg
        "Dalam Proses" -> StatusProsesBg
        else -> StatusMenungguBg
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("status/${aduan.status}")
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
                        aduan.status ?: "Menunggu Verifikasi",
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Text(aduan.id?.takeLast(8) ?: "#UNKNOWN", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Judul dan Deskripsi
            Text(
                aduan.kategoriKeluhan,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                aduan.deskripsiKeluhan,
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodySmall,
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
                Text(aduan.createdAt?.take(10) ?: "Baru saja", color = Color.DarkGray, style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
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
