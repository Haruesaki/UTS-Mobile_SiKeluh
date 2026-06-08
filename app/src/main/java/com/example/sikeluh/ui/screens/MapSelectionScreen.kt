package com.example.sikeluh.ui.screens

import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.sikeluh.ui.theme.AccentGreen
import com.example.sikeluh.ui.theme.PrimaryTeal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSelectionScreen(navController: NavController) {
    val context = LocalContext.current
    
    // Initialize OSMdroid configuration
    Configuration.getInstance().userAgentValue = context.packageName

    val mapView = remember { MapView(context) }
    val myLocationOverlay = remember { MyLocationNewOverlay(GpsMyLocationProvider(context), mapView) }
    
    var centerLocation by remember { mutableStateOf(GeoPoint(-5.3971, 105.2668)) }
    var addressPreview by remember { mutableStateOf("Mencari alamat...") }
    var fullAddressObj by remember { mutableStateOf<android.location.Address?>(null) }

    var hasLocationPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions.values.all { it }
        if (hasLocationPermission) {
            myLocationOverlay.enableMyLocation()
            myLocationOverlay.enableFollowLocation()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        
        // Initial center update after view is ready
        delay(1000)
        centerLocation = mapView.mapCenter as GeoPoint
    }

    // Reverse Geocoding Effect
    LaunchedEffect(centerLocation) {
        delay(800) // Debounce to avoid excessive API calls
        withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(centerLocation.latitude, centerLocation.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val addr = addresses[0]
                    fullAddressObj = addr
                    addressPreview = addr.getAddressLine(0) ?: "Alamat tidak ditemukan"
                } else {
                    addressPreview = "Alamat tidak ditemukan"
                }
            } catch (e: Exception) {
                addressPreview = "Gagal mengambil alamat"
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            myLocationOverlay.disableMyLocation()
            myLocationOverlay.disableFollowLocation()
            mapView.onPause()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pilih Lokasi", style = MaterialTheme.typography.titleLarge, color = PrimaryTeal) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            AndroidView(
                factory = {
                    mapView.apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(15.0) // Set to exactly 15.0 as requested
                        controller.setCenter(centerLocation)
                        
                        addMapListener(object : MapListener {
                            override fun onScroll(event: ScrollEvent?): Boolean {
                                centerLocation = mapCenter as GeoPoint
                                return true
                            }
                            override fun onZoom(event: ZoomEvent?): Boolean {
                                centerLocation = mapCenter as GeoPoint
                                return true
                            }
                        })

                        overlays.add(myLocationOverlay)
                        
                        if (hasLocationPermission) {
                            myLocationOverlay.enableMyLocation()
                            myLocationOverlay.enableFollowLocation()
                        }
                    }
                },
                update = {
                    it.onResume()
                },
                modifier = Modifier.fillMaxSize()
            )

            // Center Marker (Static crosshair)
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .offset(y = (-24).dp)
            )

            // Address Preview and Confirm Button Container
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Address Preview Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Lokasi Terpilih:",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = addressPreview,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Confirm Button
                Button(
                    onClick = {
                        val center = mapView.mapCenter as GeoPoint
                        navController.previousBackStackEntry?.savedStateHandle?.let { handle ->
                            handle.set("lat", center.latitude)
                            handle.set("lng", center.longitude)
                            handle.set("address", addressPreview)
                            // Pass components for auto-fill
                            fullAddressObj?.let { addr ->
                                handle.set("provinsi", addr.adminArea ?: "")
                                handle.set("kota", addr.locality ?: addr.subAdminArea ?: "")
                                handle.set("kecamatan", addr.subLocality ?: "")
                            }
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Konfirmasi Lokasi", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
