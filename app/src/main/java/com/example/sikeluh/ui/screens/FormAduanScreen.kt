package com.example.sikeluh.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*
import com.example.sikeluh.viewmodel.AduanViewModel
import com.example.sikeluh.viewmodel.AuthViewModel
import com.example.sikeluh.model.Aduan
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAduanScreen(
    navController: NavController, 
    viewModel: AduanViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val user by authViewModel.currentUser.collectAsState()
    
    // Initialize OSMdroid configuration
    org.osmdroid.config.Configuration.getInstance().userAgentValue = context.packageName
    
    // Form State
    var kategori by rememberSaveable { mutableStateOf("") }
    var deskripsi by rememberSaveable { mutableStateOf("") }
    var provinsi by rememberSaveable { mutableStateOf("") }
    var kota by rememberSaveable { mutableStateOf("") }
    var kecamatan by rememberSaveable { mutableStateOf("") }
    var latitude by rememberSaveable { mutableStateOf<Double?>(null) }
    var longitude by rememberSaveable { mutableStateOf<Double?>(null) }
    var alamatLengkap by rememberSaveable { mutableStateOf("") }

    // Dropdown State
    val kategoriOptions = listOf(
        "kebersihan lingkungan",
        "kemanan dan kebersihan",
        "lalu lintas dan transportasi",
        "penerangan jalan",
        "pelayanan publik"
    )
    var expanded by remember { mutableStateOf(false) }

    // Image Upload State
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var tempImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var showImageSourceDialog by remember { mutableStateOf(false) }
    var showCameraPreviewDialog by remember { mutableStateOf(false) }

    // Helper to create a temporary file for the camera
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    // Launchers
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            showCameraPreviewDialog = true
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val file = createImageFile(context)
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            tempImageUri = uri
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
        }
    }

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
                    
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = kategori,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("Pilih Kategori", style = MaterialTheme.typography.bodyMedium) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            containerColor = Color.White
                        ) {
                            kategoriOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { 
                                        Text(
                                            option.split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Black
                                        ) 
                                    },
                                    onClick = {
                                        kategori = option
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }

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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .clickable { showImageSourceDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedImageUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(selectedImageUri),
                                contentDescription = "Selected Image",
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.CameraAlt, null, tint = Color.Gray)
                                Text("Unggah Foto", color = Color.Gray, style = MaterialTheme.typography.labelMedium)
                            }
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
                    if (kategori.isBlank() || deskripsi.isBlank() || (latitude == null) || (longitude == null) ||
                        provinsi.isBlank() || kota.isBlank() || kecamatan.isBlank()) {
                        Toast.makeText(context, "Mohon lengkapi semua data dan pilih lokasi", Toast.LENGTH_SHORT).show()
                    } else {
                        val aduan = Aduan(
                            kategoriKeluhan = kategori,
                            deskripsiKeluhan = deskripsi,
                            lampiranFoto = null, // Will be set in ViewModel after upload
                            lokasiAduan = alamatLengkap,
                            alamatProvinsi = provinsi,
                            alamatKota = kota,
                            alamatKecamatan = kecamatan,
                            idPengguna = user?.id,
                            status = "Menunggu Verifikasi"
                        )
                        
                        // Convert URI to ByteArray if exists
                        val imageBytes = selectedImageUri?.let { uri ->
                            try {
                                context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                null
                            }
                        }
                        
                        viewModel.submitAduan(aduan, imageBytes) { success, errorMsg ->
                            if (success) {
                                Toast.makeText(context, "Aduan Berhasil Dikirim", Toast.LENGTH_LONG).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_LONG).show()
                            }
                        }
                        Toast.makeText(context, "Sedang mengirim aduan...", Toast.LENGTH_SHORT).show()
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

    // Dialog: Pilih Sumber Gambar
    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text("Pilih Sumber Gambar") },
            text = {
                Column {
                    ListItem(
                        headlineContent = { Text("Ambil dari Kamera") },
                        leadingContent = { Icon(Icons.Default.CameraAlt, contentDescription = null) },
                        modifier = Modifier.clickable {
                            showImageSourceDialog = false
                            val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                val file = createImageFile(context)
                                val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                                tempImageUri = uri
                                cameraLauncher.launch(uri)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    )
                    ListItem(
                        headlineContent = { Text("Pilih dari Galeri") },
                        leadingContent = { Icon(Icons.Default.PhotoLibrary, contentDescription = null) },
                        modifier = Modifier.clickable {
                            showImageSourceDialog = false
                            galleryLauncher.launch("image/*")
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showImageSourceDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    // Dialog: Preview Kamera
    if (showCameraPreviewDialog && tempImageUri != null) {
        AlertDialog(
            onDismissRequest = { showCameraPreviewDialog = false },
            title = { Text("Preview Gambar") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = rememberAsyncImagePainter(tempImageUri),
                        contentDescription = "Preview Image",
                        modifier = Modifier.fillMaxWidth().height(300.dp).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedImageUri = tempImageUri
                    showCameraPreviewDialog = false
                }) {
                    Text("Pilih Gambar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showCameraPreviewDialog = false
                    // Relaunch camera
                    cameraLauncher.launch(tempImageUri!!)
                }) {
                    Text("Ambil Ulang")
                }
            }
        )
    }
}
