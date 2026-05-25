plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.sikeluh"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.sikeluh"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.kotlinx.serialization.json)
    implementation("androidx.navigation:navigation-compose:2.8.8")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Tambahkan BOM (Bill of Materials) agar versi library Supabase seragam
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.3")) // Cek versi terbaru jika perlu

// Modul Postgrest untuk operasi CRUD Database
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.4.3")

// Mesin HTTP Ktor (Wajib untuk Supabase SDK di Android)
    implementation("io.ktor:ktor-client-android:2.3.11")
    implementation("io.ktor:ktor-client-core:2.3.11")
    // OpenStreetMap (OSMDroid)
    implementation("org.osmdroid:osmdroid-android:6.1.18")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}