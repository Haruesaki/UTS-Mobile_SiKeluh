package com.example.sikeluh.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.R
import com.example.sikeluh.ui.components.*
import com.example.sikeluh.ui.theme.*
import com.example.sikeluh.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    var namaLengkap by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agreeTerms by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Show error message when it changes
    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // Small Logo Section
        Image(
            painter = painterResource(id = R.drawable.logo_sikeluh),
            contentDescription = "Si Keluh Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Selamat Datang", color = Color.White, style = MaterialTheme.typography.headlineLarge)
        Text(
            text = "Sistem Pengaduan Pemerintahan\nKota Bandar Lampung",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text("Daftar", color = Color.White, style = MaterialTheme.typography.displaySmall)

        Spacer(modifier = Modifier.height(24.dp))

        // Input Fields
        AuthTextField(
            value = namaLengkap,
            onValueChange = { namaLengkap = it },
            placeholder = "Nama Lengkap"
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = nik,
            onValueChange = { nik = it },
            placeholder = "NIK"
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Terms & Conditions
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = agreeTerms,
                onCheckedChange = { agreeTerms = it },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.White,
                    checkedColor = Color.White,
                    checkmarkColor = AuthBg
                )
            )
            Text("I agree to the Terms & Conditions", color = Color.White, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Created Account Button
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White
            )
        } else {
            AuthSubmitButton(
                text = "Created Account",
                enabled = !isLoading,
                onClick = { 
                    viewModel.register(nik, password, namaLengkap, agreeTerms) {
                        navController.navigate("home") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    SiKeluhTheme {
        RegisterScreen(rememberNavController())
    }
}
