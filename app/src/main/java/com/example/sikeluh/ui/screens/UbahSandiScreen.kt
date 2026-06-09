package com.example.sikeluh.ui.screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sikeluh.ui.components.BottomNavigationBar
import com.example.sikeluh.ui.theme.*
import com.example.sikeluh.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    var currentPasswordError by remember { mutableStateOf<String?>(null) }

    // Logika Persyaratan Kata Sandi
    val isMinLengthMet = newPassword.length >= 8
    val hasNumber = newPassword.any { it.isDigit() }
    val hasSymbol = newPassword.any { !it.isLetterOrDigit() }
    val allRequirementsMet = isMinLengthMet && hasNumber && hasSymbol

    // Perhitungan Kekuatan
    val strengthScore = listOf(isMinLengthMet, hasNumber, hasSymbol).count { it }
    val strengthText = when {
        newPassword.isEmpty() -> ""
        strengthScore <= 1 -> "Lemah"
        strengthScore == 2 -> "Sedang"
        else -> "Kuat"
    }
    val strengthColor = when {
        strengthScore <= 1 -> Color(0xFFB71C1C) // Merah
        strengthScore == 2 -> Color(0xFFFBC02D) // Kuning/Oranye
        else -> AccentGreen // Hijau
    }
    val strengthProgress by animateFloatAsState(
        targetValue = if (newPassword.isEmpty()) 0f else strengthScore / 3f,
        label = "strengthProgress"
    )
    val animatedStrengthColor by animateColorAsState(targetValue = strengthColor, label = "strengthColor")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Ubah Kata Sandi", 
                        style = MaterialTheme.typography.titleLarge, 
                        color = PrimaryTeal, 
                        fontWeight = FontWeight.Bold 
                    ) 
                },
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
                .padding(20.dp)
        ) {
            Text(
                "Demi keamanan akun Anda, harap rutin mengubah kata sandi dan jangan gunakan kata sandi yang sama untuk aplikasi lain.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Kata Sandi Saat Ini
            PasswordInputField(
                label = "Kata Sandi Saat Ini",
                value = currentPassword,
                onValueChange = { 
                    currentPassword = it 
                    currentPasswordError = null // Hapus kesalahan saat mengetik
                },
                placeholder = "Masukkan kata sandi saat ini",
                isVisible = currentPasswordVisible,
                onVisibilityChange = { currentPasswordVisible = !currentPasswordVisible },
                error = currentPasswordError
            )

            TextButton(
                onClick = { /* TODO */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Lupa Kata Sandi?", color = PrimaryTeal, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Kata Sandi Baru
            PasswordInputField(
                label = "Kata Sandi Baru",
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = "Masukkan kata sandi baru",
                isVisible = newPasswordVisible,
                onVisibilityChange = { newPasswordVisible = !newPasswordVisible }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Indikator Kekuatan Kata Sandi
            if (newPassword.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.LightGray.copy(alpha = 0.3f))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(strengthProgress)
                                    .fillMaxHeight()
                                    .background(animatedStrengthColor)
                            )
                        }
                        Text(
                            strengthText, 
                            color = animatedStrengthColor, 
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Konfirmasi Kata Sandi
            PasswordInputField(
                label = "Konfirmasi Kata Sandi Baru",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Ketik ulang kata sandi baru",
                isVisible = confirmPasswordVisible,
                onVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Kartu Persyaratan
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Persyaratan Kata Sandi:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    RequirementItem(text = "Minimal 8 karakter", isMet = isMinLengthMet)
                    RequirementItem(text = "Mengandung setidaknya 1 angka", isMet = hasNumber)
                    RequirementItem(text = "Mengandung setidaknya 1 simbol (contoh: @, #, $, dsb)", isMet = hasSymbol)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Tombol Kirim
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = PrimaryTeal)
            } else {
                Button(
                    onClick = {
                        if (!allRequirementsMet) {
                            Toast.makeText(context, "Mohon penuhi semua persyaratan kata sandi", Toast.LENGTH_SHORT).show()
                        } else if (newPassword != confirmPassword) {
                            Toast.makeText(context, "Konfirmasi kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.changePassword(
                                currentPass = currentPassword,
                                newPass = newPassword,
                                onSuccess = {
                                    Toast.makeText(context, "Kata sandi berhasil diubah", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                },
                                onError = { errorMsg ->
                                    currentPasswordError = errorMsg
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
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
                            Icon(Icons.Default.Save, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Simpan Perubahan", color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PasswordInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isVisible: Boolean,
    onVisibilityChange: () -> Unit,
    error: String? = null
) {
    Column {
        Text(label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = DarkNavy)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.Gray) },
            trailingIcon = {
                IconButton(onClick = onVisibilityChange) {
                    Icon(
                        imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isVisible) "Hide password" else "Show password",
                        tint = Color.Gray
                    )
                }
            },
            isError = error != null,
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = PrimaryTeal,
                unfocusedBorderColor = Color.LightGray,
                errorBorderColor = Color.Red
            ),
            singleLine = true
        )
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun RequirementItem(text: String, isMet: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .border(1.5.dp, if (isMet) AccentGreen else Color.Gray, CircleShape)
                .background(if (isMet) AccentGreen else Color.Transparent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isMet) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text, 
            style = MaterialTheme.typography.bodyMedium, 
            color = if (isMet) Color.Black else Color.DarkGray,
            fontWeight = if (isMet) FontWeight.Bold else FontWeight.Normal
        )
    }
}
