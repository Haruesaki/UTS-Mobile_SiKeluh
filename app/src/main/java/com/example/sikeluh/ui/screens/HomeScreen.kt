package com.example.sikeluh.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.sikeluh.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*
import com.example.sikeluh.viewmodel.AduanViewModel
import com.example.sikeluh.model.Aduan
import com.example.sikeluh.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    navController: NavController, 
    viewModel: AduanViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val aduans by viewModel.aduans.collectAsState()
    val user by authViewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAduans()
    }

    // Dummy data for "Jelajahi Aduan" matching design reference
    val exploreDummyAduans = listOf(
        Aduan(
            kategoriKeluhan = "Lalu Lintas dan Transportasi",
            deskripsiKeluhan = "Jalan Rusak",
            lokasiAduan = "Jl. Merdeka No.123, Kecamatan lalauI",
            alamatProvinsi = "Lampung",
            alamatKota = "Bandar Lampung",
            alamatKecamatan = "Kecamatan lalauI",
            status = "Selesai",
            createdAt = "2021-09-09",
            lampiranFoto = null
        ),
        Aduan(
            kategoriKeluhan = "Penerangan Jalan",
            deskripsiKeluhan = "Jalan Rusak",
            lokasiAduan = "Jl. Merdeka No.123, Kecamatan lalauI",
            alamatProvinsi = "Lampung",
            alamatKota = "Bandar Lampung",
            alamatKecamatan = "Kecamatan lalauI",
            status = "Selesai",
            createdAt = "2021-09-09",
            lampiranFoto = null
        )
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Header Wrapper - Fixed position logic
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF538C8C))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Picture in top bar
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .shadow(2.dp, CircleShape)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    ) {
                        SubcomposeAsyncImage(
                            model = user?.fotoProfil,
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop,
                            loading = { CircularProgressIndicator(modifier = Modifier.padding(10.dp), color = Color.White, strokeWidth = 1.dp) },
                            error = { Image(painterResource(R.drawable.pp), "Default") },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    
                    // Logo Drawable in top bar
                    Image(
                        painter = painterResource(id = R.drawable.logo_sikeluh),
                        contentDescription = "Logo",
                        modifier = Modifier.size(45.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Greeting
                item {
                    Column {
                        Text(
                            text = "Hallo ${user?.nama?.split(" ")?.get(0) ?: "User"} 👋", 
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Sampaikan keluhanmu,\nkami teruskan ke Pemerintah Daerah", 
                            color = Color.Gray, 
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // Main Banner Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Increased height to ensure button fits
                            .shadow(8.dp, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(Color(0xFF538C8C), PrimaryDark)
                                    )
                                )
                                .padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.weight(1.3f),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        "Keluhan Anda,\nTugas Kami,\nTindak Lanjut Mereka",
                                        color = Color.White,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 22.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "Adukan masalah di sekitar anda,\nkami teruskan langsung ke Pemerintah Daerah",
                                        color = Color.White.copy(alpha = 0.8f),
                                        style = MaterialTheme.typography.labelSmall,
                                        lineHeight = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Button(
                                        onClick = { navController.navigate("form") },
                                        colors = ButtonDefaults.buttonColors(containerColor = AccentGreen),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.height(42.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp)
                                    ) {
                                        Icon(Icons.Default.EditNote, null, Modifier.size(20.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Buat Aduan Baru", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, Modifier.size(16.dp))
                                    }
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.istana),
                                    contentDescription = null,
                                    modifier = Modifier.weight(0.7f).size(120.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }

                // Aduan Saya Section (Removed "Lihat Semua")
                item { 
                    Text(
                        text = "Aduan Saya", 
                        style = MaterialTheme.typography.titleLarge, 
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                val userAduans = aduans.filter { it.idPengguna == user?.id }
                if (userAduans.isEmpty()) {
                    item {
                        Text(
                            "Belum ada aduan terbaru",
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                } else {
                    items(userAduans.take(1)) { aduan ->
                        // Displays REAL database data with Remote Image
                        AduanCardNew(aduan, navController)
                    }
                }

                // Jelajahi Aduan Section (Removed "Lihat Semua")
                item { 
                    Text(
                        text = "Jelajahi Aduan", 
                        style = MaterialTheme.typography.titleLarge, 
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                items(exploreDummyAduans) { dummy ->
                    // Displays Dummy Data with local drawable images
                    AduanCardNew(
                        aduan = dummy, 
                        navController = navController, 
                        isDummy = true,
                        dummyImageRes = R.drawable.jalanbagus
                    )
                }
            }
        }
    }
}

@Composable
fun AduanCardNew(
    aduan: Aduan, 
    navController: NavController, 
    isDummy: Boolean = false,
    dummyImageRes: Int = R.drawable.jalanrusak
) {
    val statusColor = when (aduan.status) {
        "Selesai" -> AduanSelesai
        "Dalam Proses" -> Color(0xFF64B5F6)
        else -> Color(0xFFFFA726)
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { 
                if (!isDummy) navController.navigate("status/${aduan.status}")
            }
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF538C8C), PrimaryDark)
                    )
                )
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image handling: Local Drawable for dummy, Remote URL for real
                if (isDummy) {
                    Image(
                        painter = painterResource(id = dummyImageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    )
                } else {
                    SubcomposeAsyncImage(
                        model = aduan.lampiranFoto,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.1f)),
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 1.dp)
                            }
                        },
                        error = {
                            Image(
                                painter = painterResource(id = R.drawable.jalanrusak),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Content
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        color = statusColor,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = aduan.status ?: "Menunggu Verifikasi",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = aduan.deskripsiKeluhan,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Text(
                        text = aduan.lokasiAduan,
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.AccessTime, 
                            null, 
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isDummy) aduan.createdAt ?: "" else aduan.createdAt?.take(10) ?: "Baru saja",
                            color = Color.White.copy(alpha = 0.7f),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                
                // More Icon
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Top)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    SiKeluhTheme {
        HomeScreen(navController = navController)
    }
}
