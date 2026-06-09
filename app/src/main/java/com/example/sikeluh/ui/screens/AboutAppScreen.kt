package com.example.sikeluh.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sikeluh.R
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.PrimaryTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Tentang Aplikasi",
                        style = MaterialTheme.typography.titleLarge,
                        color = PrimaryTeal,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryTeal
                        )
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
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.logo_aplikasi),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = "Si Keluh",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // App Version
            Surface(
                color = Color(0xFFE2E8F0),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Versi 1.1.0",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Description Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "SiKeluh adalah aplikasi pengaduan masyarakat berbasis Android yang membantu warga Kota Bandar Lampung melaporkan berbagai permasalahan fasilitas umum dan pelayanan publik secara mudah, cepat, dan transparan. Melalui aplikasi ini, pengguna dapat mengirim laporan seperti jalan rusak, lampu lalu lintas bermasalah, banjir, sampah, dan kerusakan fasilitas umum lainnya. Pengguna juga dapat memantau status laporan, melihat progress penanganan, serta melihat bukti penyelesaian dan riwayat perbaikan yang dilakukan pemerintah. SiKeluh Balam dikembangkan untuk meningkatkan kualitas pelayanan publik, transparansi pemerintah, dan partisipasi masyarakat dalam menjaga fasilitas kota.",
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Justify
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
