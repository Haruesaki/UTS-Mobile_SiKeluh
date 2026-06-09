package com.example.sikeluh.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sikeluh.R
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*
import com.example.sikeluh.viewmodel.AuthViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val user by viewModel.currentUser.collectAsState()
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    var nama by remember { mutableStateOf(user?.nama ?: "") }
    var nik by remember { mutableStateOf(user?.nik ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var telepon by remember { mutableStateOf(user?.nomorTelepon ?: "") }
    var alamat by remember { mutableStateOf(user?.alamat ?: "") }
    
    // Profile Image State
    var selectedImageUri by remember { mutableStateOf<Uri?>(user?.fotoProfil?.let { Uri.parse(it) }) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }
    var pendingImageUri by remember { mutableStateOf<Uri?>(null) }
    var showImageSourceDialog by remember { mutableStateOf(false) }
    var showCropDialog by remember { mutableStateOf(false) }

    // Helper for camera file
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PROFILE_${timeStamp}_", ".jpg", storageDir)
    }

    // Launchers
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            pendingImageUri = uri
            showCropDialog = true
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            pendingImageUri = tempImageUri
            showCropDialog = true
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profil", style = MaterialTheme.typography.titleLarge, color = PrimaryTeal, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryTeal)
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image Section (1:1 Ratio)
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(150.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(4.dp, Color.White, CircleShape)
                            .shadow(4.dp, CircleShape),
                        contentScale = ContentScale.Crop // Ensures 1:1 display
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.pp),
                        contentDescription = "Default Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(4.dp, Color.White, CircleShape)
                            .shadow(4.dp, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                
                // Camera Icon overlay
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .offset(x = (-4).dp, y = (-4).dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(PrimaryDark)
                        .border(1.dp, Color.White, RoundedCornerShape(12.dp))
                        .clickable { showImageSourceDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Change Photo",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Text(
                "Ketuk untuk ubah foto",
                style = MaterialTheme.typography.labelMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Form Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EditField(label = "Nama Lengkap", value = nama, onValueChange = { nama = it })
                    
                    // NIK Field (Locked/Disabled)
                    Column {
                        Text("Nomor Induk Kependudukan (NIK)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = DarkNavy)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = nik,
                            onValueChange = {},
                            readOnly = true, // Lock the field
                            enabled = false, // Visual cue for locked field
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledContainerColor = Color(0xFFF1F5F9),
                                disabledTextColor = Color.Black, // Make it readable even if disabled
                                disabledBorderColor = Color.LightGray
                            )
                        )
                    }

                    EditField(label = "Email", value = email, onValueChange = { email = it }, placeholder = "contoh@gmail.com")
                    
                    Column {
                        Text("Nomor Telepon", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = DarkNavy)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .height(56.dp)
                                    .width(60.dp)
                                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                                    .background(Color(0xFFF1F5F9))
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("+62", fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                            OutlinedTextField(
                                value = telepon,
                                onValueChange = { telepon = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("81234567890", color = Color.Gray) },
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedBorderColor = Color.LightGray,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                        }
                    }

                    Column {
                        Text("Alamat", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = DarkNavy)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = alamat,
                            onValueChange = { alamat = it },
                            modifier = Modifier.fillMaxWidth().height(120.dp),
                            placeholder = { Text("Masukkan alamat lengkap...", color = Color.Gray) },
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF8FAFC),
                                unfocusedContainerColor = Color(0xFFF8FAFC),
                                unfocusedBorderColor = Color.LightGray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            )
                        )
                    }
                }
            }

            // Submit Button
            if (isLoading) {
                CircularProgressIndicator(color = PrimaryTeal)
            } else {
                Button(
                    onClick = {
                        viewModel.updateProfile(nama, nik, email, telepon, alamat, selectedImageUri?.toString()) {
                            Toast.makeText(context, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(PrimaryDark, Color(0xFF003D3B))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Simpan Perubahan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
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

    // Dialog: Crop Preview (1:1 Ratio with Manual Positioning)
    if (showCropDialog && pendingImageUri != null) {
        var scale by remember { mutableStateOf(1f) }
        var offset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }

        AlertDialog(
            onDismissRequest = { showCropDialog = false },
            title = { Text("Atur Posisi Foto Profil") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Gunakan 2 jari untuk zoom/geser foto ke dalam lingkaran", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // The Cropping Area
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .clip(CircleShape)
                            .border(2.dp, PrimaryTeal, CircleShape)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(pendingImageUri),
                            contentDescription = "Manual Crop Preview",
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTransformGestures { _, pan, zoom, _ ->
                                        scale *= zoom
                                        offset += pan
                                    }
                                }
                                .graphicsLayer(
                                    scaleX = maxOf(1f, scale),
                                    scaleY = maxOf(1f, scale),
                                    translationX = offset.x,
                                    translationY = offset.y
                                ),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Pastikan wajah berada di tengah", style = MaterialTheme.typography.labelSmall, color = PrimaryTeal)
                }
            },
            confirmButton = {
                IconButton(
                    onClick = {
                        selectedImageUri = pendingImageUri
                        showCropDialog = false
                    },
                    modifier = Modifier.background(AccentGreen, CircleShape)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Confirm", tint = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCropDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun EditField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String = "") {
    Column {
        Text(label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = DarkNavy)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.Gray) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF8FAFC),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            singleLine = true
        )
    }
}
