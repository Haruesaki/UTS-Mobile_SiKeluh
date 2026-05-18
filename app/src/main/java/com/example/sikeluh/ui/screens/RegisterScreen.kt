package com.example.sikeluh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sikeluh.R
import com.example.sikeluh.ui.components.*
import com.example.sikeluh.ui.theme.*

@Composable
fun RegisterScreen(navController: NavController) {
    var namaLengkap by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agreeTerms by remember { mutableStateOf(false) }

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

        Text("Selamat Datang", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Sistem Pengaduan Pemerintahan\nKota Bandar Lampung",
            color = Color.White,
            fontSize = 16.sp,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text("Daftar", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)

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
            Text("I agree to the Terms & Conditions", color = Color.White, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Created Account Button
        AuthSubmitButton(
            text = "Created Account",
            onClick = { navController.navigate("home") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    SiKeluhTheme {
        RegisterScreen(rememberNavController())
    }
}
