package com.example.sikeluh.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*
import com.example.sikeluh.viewmodel.AduanViewModel
import com.example.sikeluh.model.Aduan
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAduanScreen(navController: NavController, viewModel: AduanViewModel = viewModel()) {
    val context = LocalContext.current
    
    // Initialize OSMdroid configuration
    org.osmdroid.config.Configuration.getInstance().userAgentValue = context.packageName
    
    // Form State
    var kategori by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var provinsi by remember { mutableStateOf("") }
    var kota by remember { mutableStateOf("") }
    var kecamatan by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var alamatLengkap by remember { mutableStateOf("") }

    // Observe result from MapSelectionScreen
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.getLiveData<Double>("lat")?.observeForever { latitude = it }
        savedStateHandle?.getLiveData<Double>("lng")?.observeForever { longitude = it }
        savedStateHandle?.getLiveData<String>("address")?.observeForever { alamatLengkap = it }
        // Auto-fill address components
        savedStateHandle?.getLiveData<String>("provinsi")?.observeForever { provinsi = it }
        savedStateHandle?.getLiveData<String>("kota")?.observeForever { kota = it }
        savedStateHandle?.getLiveData<String>("kecamatan")?.observeForever { kecamatan = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Form Aduan", style = MaterialTheme.typography.titleLarge, color = PrimaryTeal) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Aduan Keluhan", style = MaterialTheme.typography.displaySmall)
            Text("Sampaikan Aduan Anda dengan detail yang jelas...", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Kategori Keluhan", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = kategori, 
                        onValueChange = { kategori = it }, 
                        placeholder = { Text("Pilih Kategori", style = MaterialTheme.typography.bodyMedium) }, 
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                    Text("Deskripsi Keluhan", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = deskripsi, 
                        onValueChange = { deskripsi = it }, 
                        placeholder = { Text("Jelaskan secara detail...", style = MaterialTheme.typography.bodyMedium) }, 
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                    Text("Lampiran Foto", style = MaterialTheme.typography.titleMedium)
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.dp).border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CameraAlt, null, tint = Color.Gray)
                            Text("Unggah Foto", color = Color.Gray, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Lokasi Aduan", style = MaterialTheme.typography.titleMedium)
                    
                    if (latitude != null && longitude != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(8.dp))
                        ) {
                            AndroidView(
                                factory = { ctx ->
                                    MapView(ctx).apply {
                                        setTileSource(TileSourceFactory.MAPNIK)
                                        setMultiTouchControls(false)
                                        controller.setZoom(15.0)
                                        controller.setCenter(GeoPoint(latitude!!, longitude!!))
                                        
                                        // Add a marker to show the selected point
                                        val marker = org.osmdroid.views.overlay.Marker(this)
                                        marker.position = GeoPoint(latitude!!, longitude!!)
                                        marker.setAnchor(org.osmdroid.views.overlay.Marker.ANCHOR_CENTER, org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM)
                                        overlays.add(marker)
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                            // Clickable overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { navController.navigate("map_selection") }
                            )
                        }
                        Text(
                            text = alamatLengkap,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "Klik peta untuk mengubah lokasi",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = { navController.navigate("map_selection") }, 
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                            ) {
                                Icon(Icons.Default.LocationOn, null)
                                Text("Pilih Lokasi", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                    
                    Text("Provinsi, Kota, Kecamatan", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = provinsi, 
                        onValueChange = { provinsi = it }, 
                        placeholder = { Text("Provinsi", style = MaterialTheme.typography.bodyMedium) }, 
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                    OutlinedTextField(
                        value = kota, 
                        onValueChange = { kota = it }, 
                        placeholder = { Text("Kota", style = MaterialTheme.typography.bodyMedium) }, 
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                    OutlinedTextField(
                        value = kecamatan, 
                        onValueChange = { kecamatan = it }, 
                        placeholder = { Text("Kecamatan", style = MaterialTheme.typography.bodyMedium) }, 
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                }
            }

            Button(
                onClick = {
                    if (kategori.isBlank() || deskripsi.isBlank() || (latitude == null) || (longitude == null)) {
                        Toast.makeText(context, "Mohon lengkapi data dan pilih lokasi", Toast.LENGTH_SHORT).show()
                    } else {
                        val aduan = Aduan(
                            kategoriKeluhan = kategori,
                            deskripsiKeluhan = deskripsi,
                            lokasiAduan = alamatLengkap,
                            alamatProvinsi = provinsi,
                            alamatKota = kota,
                            alamatKecamatan = kecamatan,
                            status = "Menunggu Verifikasi"
                        )
                        viewModel.submitAduan(aduan)
                        Toast.makeText(context, "Aduan Berhasil Dikirim", Toast.LENGTH_LONG).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentGreen)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kirim Aduan", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
