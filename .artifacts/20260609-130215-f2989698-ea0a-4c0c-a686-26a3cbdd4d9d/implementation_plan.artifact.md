# Implementasi Data Dinamis pada Status Aduan

Rencana ini bertujuan untuk mengganti data dummy pada `StatusAduanScreen` dengan data asli dari database Supabase. Kita akan mengubah mekanisme navigasi untuk mengirim `aduanId` sehingga semua detail (kategori, lokasi, alamat lengkap, dan status) dapat diambil secara akurat.

## Poin Kritikal
- Mengubah rute navigasi dari `status/{status}` menjadi `status/{aduanId}`.
- Memperbarui `StatusAduanScreen` untuk mengambil data aduan berdasarkan ID.
- Memperbarui semua pemicu navigasi di `HomeScreen` dan `RiwayatAduanScreen`.

## Perubahan yang Diusulkan

### 1. Navigasi & Rute

#### [MainActivity.kt](file:///C:/Kuliah/coding/Android Studio/SiKeluh/app/src/main/java/com/example/sikeluh/MainActivity.kt)
- Ubah definisi rute `status/{status}` menjadi `status/{aduanId}`.
- Gunakan `aduanId` untuk menginisialisasi `StatusAduanScreen`.

### 2. UI & Logika Pengambilan Data

#### [StatusAduanScreen.kt](file:///C:/Kuliah/coding/Android Studio/SiKeluh/app/src/main/java/com/example/sikeluh/ui/screens/StatusAduanScreen.kt)
- Ubah parameter fungsi dari `status: String` menjadi `aduanId: String`.
- Tambahkan `AduanViewModel` sebagai parameter.
- Gunakan `LaunchedEffect` untuk mencari data aduan dari `viewModel.aduans` berdasarkan `aduanId`.
- Ganti teks dummy (kategori, deskripsi, alamat, tanggal) dengan properti dari objek `aduan` yang ditemukan.
- Ganti logika langkah progress untuk menggunakan status asli dari objek `aduan`.

### 3. Pembaruan Pemicu Navigasi

#### [HomeScreen.kt](file:///C:/Kuliah/coding/Android Studio/SiKeluh/app/src/main/java/com/example/sikeluh/ui/screens/HomeScreen.kt)
- Di dalam `AduanCardNew`, ubah `navController.navigate("status/${aduan.status}")` menjadi `navController.navigate("status/${aduan.id}")`.

#### [RiwayatAduanScreen.kt](file:///C:/Kuliah/coding/Android Studio/SiKeluh/app/src/main/java/com/example/sikeluh/ui/screens/RiwayatAduanScreen.kt)
- Di dalam `AduanItemCardReal`, ubah `navController.navigate("status/${aduan.status}")` menjadi `navController.navigate("status/${aduan.id}")`.

---

## Rencana Verifikasi

### Verifikasi Manual
1. Buka aplikasi dan masuk.
2. Di halaman Beranda atau Riwayat, klik salah satu kartu aduan asli (bukan dummy).
3. Pastikan halaman `Status Aduan` menampilkan judul/kategori dan alamat yang sesuai dengan data yang diklik.
4. Periksa apakah garis progress status mencerminkan status asli dari database (misal: "Dalam Proses").
5. Pastikan navigasi kembali (back button) berfungsi normal.
