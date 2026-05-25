package com.example.sikeluh.ui.screens

import android.content.Context
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.sikeluh.ui.theme.AccentGreen
import com.example.sikeluh.ui.theme.PrimaryTeal
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSelectionScreen(navController: NavController) {
    val context = LocalContext.current
    
    // Initialize OSMdroid configuration
    Configuration.getInstance().userAgentValue = context.packageName

    val mapView = remember { MapView(context) }
    var selectedLocation by remember { mutableStateOf(GeoPoint(-5.3971, 105.2668)) } // Default: Bandar Lampung

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
                        controller.setZoom(15.0)
                        controller.setCenter(selectedLocation)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // Center Marker (Static)
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .offset(y = (-24).dp) // Offset to make the tip point to center
            )

            // Confirm Button
            Button(
                onClick = {
                    val center = mapView.mapCenter as GeoPoint
                    navController.previousBackStackEntry?.savedStateHandle?.set("lat", center.latitude)
                    navController.previousBackStackEntry?.savedStateHandle?.set("lng", center.longitude)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
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
