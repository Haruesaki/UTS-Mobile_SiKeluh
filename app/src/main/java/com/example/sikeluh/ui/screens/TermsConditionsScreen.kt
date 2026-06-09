package com.example.sikeluh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.PrimaryDark
import com.example.sikeluh.ui.theme.PrimaryTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsConditionsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Syarat & Ketentuan",
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    // Header Bar inside Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(PrimaryDark)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Syarat & Ketentuan",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Content
                    Text(
                        text = "Dengan menggunakan aplikasi SiKeluh Balam, pengguna setuju untuk menggunakan aplikasi secara bijak dan bertanggung jawab. Pengguna wajib memberikan informasi laporan yang benar, jelas, dan tidak mengandung unsur hoaks, fitnah, ujaran kebencian, atau konten yang melanggar hukum. Setiap laporan yang dikirim menjadi tanggung jawab pengguna sepenuhnya. Pengguna dilarang menyalahgunakan aplikasi untuk kepentingan pribadi, spam, atau tindakan yang dapat merugikan pihak lain maupun pemerintah. Pihak pengelola aplikasi berhak menolak, menghapus, atau menindaklanjuti laporan yang dianggap tidak sesuai dengan ketentuan yang berlaku. Status dan proses penanganan laporan bergantung pada pihak pemerintah atau instansi terkait. SiKeluh Balam berfungsi sebagai media penghubung pengaduan masyarakat dan tidak menjamin seluruh laporan dapat langsung diselesaikan dalam waktu tertentu. Pengguna bertanggung jawab menjaga keamanan akun masing-masing, termasuk kerahasiaan password akun. Dengan menggunakan aplikasi SiKeluh Balam, pengguna dianggap telah membaca, memahami, dan menyetujui seluruh syarat dan ketentuan yang berlaku.",
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
