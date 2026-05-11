# PesanTiket — Aplikasi Pemesanan Tiket Bioskop

Aplikasi Android untuk pemesanan tiket bioskop secara online. Pengguna dapat mendaftar akun, login, memilih film beserta detail pemesanan, lalu menerima ringkasan pesanan yang dapat dibagikan atau dikirim via email.

---

## Anggota Kelompok

| Nama | NIM |
|------|-----|
| Zidan Giofando | 2411500834 |
| Fayruz Azzuhri | 2411500024 |

---

## Deskripsi Aplikasi

**PesanTiket** adalah aplikasi Android berbasis Java yang memungkinkan pengguna untuk:

- Mendaftar dan login menggunakan akun lokal
- Memilih film dari daftar bioskop yang tersedia
- Menentukan tanggal tayang, jam, jenis kursi, dan jumlah tiket
- Menambahkan pilihan makanan/minuman (extras)
- Melihat ringkasan pesanan beserta kalkulasi total harga
- Berbagi detail pesanan melalui berbagai kanal (Share, Email, Telepon, Website)

---

## Fitur Aplikasi

### Fitur Wajib
| Fitur | Keterangan |
|-------|------------|
| **2+ Activity** | LoginActivity, RegisterActivity, MainActivity, ResultActivity |
| **Explicit Intent** | Perpindahan antar Activity (Login → Main → Result) |
| **Implicit Intent** | Share, Email, Telepon, dan buka Website bioskop |
| **Form Validation** | Validasi kosong, format angka, format email, panjang karakter |
| **EditText** | Input nama, email, nomor HP, jumlah tiket, username, password |
| **Spinner** | Dropdown pilihan film |
| **RadioButton** | Pilihan jenis kursi dan jam tayang |
| **CheckBox** | Pilihan extras (Popcorn, Minuman, Combo Meal) |
| **ImageView** | Banner/poster film |

### Fitur Bonus
| Fitur | Keterangan |
|-------|------------|
| **SharedPreferences** | Menyimpan data login dan data pemesan terakhir |
| **Fragment** | `MovieInfoFragment` menampilkan info bioskop di halaman utama |
| **DatePickerDialog** | Pemilihan tanggal tayang dengan batasan tanggal minimum |
| **4× Implicit Intent** | Share teks, buka aplikasi email, buka dialer, buka browser |
| **AlertDialog** | Dialog konfirmasi sebelum pesanan diproses |
| **Login & Register** | Autentikasi pengguna menggunakan SharedPreferences |

---

## Struktur Project

```
PesanTiketBioskop/
├── app/
│   └── src/main/
│       ├── java/com/example/pesantiket/
│       │   ├── LoginActivity.java        ← Halaman login pengguna
│       │   ├── RegisterActivity.java     ← Halaman pendaftaran akun
│       │   ├── MainActivity.java         ← Form pemesanan tiket
│       │   ├── ResultActivity.java       ← Ringkasan pesanan & aksi lanjutan
│       │   └── MovieInfoFragment.java    ← Fragment info bioskop
│       └── res/
│           ├── layout/
│           │   ├── activity_login.xml
│           │   ├── activity_register.xml
│           │   ├── activity_main.xml
│           │   ├── activity_result.xml
│           │   └── fragment_movie_info.xml
│           ├── drawable/
│           │   ├── ic_movie_banner.xml
│           │   ├── edittext_background.xml
│           │   ├── edittext_dark.xml
│           │   ├── radio_button_selector.xml
│           │   ├── radio_text_selector.xml
│           │   └── spinner_background.xml
│           └── values/
│               ├── strings.xml
│               ├── colors.xml
│               └── themes.xml
├── build.gradle.kts
├── gradle/
│   └── libs.versions.toml
└── settings.gradle.kts
```

---

## Alur Program

```
[LoginActivity]
    │  (belum punya akun?)
    ├──────────────────────► [RegisterActivity]
    │                              │ (simpan ke SharedPreferences)
    │◄─────────────────────────────┘
    │
    │ (login berhasil — Explicit Intent)
    ▼
[MainActivity]
    │  • Pilih film (Spinner)
    │  • Pilih tanggal (DatePickerDialog)
    │  • Pilih jam tayang (RadioButton)
    │  • Pilih jenis kursi (RadioButton)
    │  • Isi data pemesan (EditText)
    │  • Pilih extras (CheckBox)
    │  • Validasi form
    │  • Konfirmasi (AlertDialog)
    │  • Simpan data ke SharedPreferences
    │
    │  (tombol PESAN SEKARANG — Explicit Intent)
    ▼
[ResultActivity]
    │  • Tampilkan ringkasan pesanan
    │  • Total harga (Rupiah)
    │
    ├── [btnShare]  → Implicit Intent: ACTION_SEND (share ke WhatsApp, dll.)
    ├── [btnEmail]  → Implicit Intent: ACTION_SENDTO (buka app email)
    ├── [btnCall]   → Implicit Intent: ACTION_DIAL (buka dialer bioskop)
    └── [btnWeb]    → Implicit Intent: ACTION_VIEW (buka browser ke cgv.id)
```

---

## Daftar Film Tersedia

| Film | Tahun |
|------|-------|
| Avengers: Doomsday | 2026 |
| Mission Impossible 8 | 2025 |
| Minecraft Movie | 2025 |
| Jurassic World Rebirth | 2025 |
| Superman: Legacy | 2025 |

---

## Jenis Kursi & Harga

| Jenis Kursi | Harga per Tiket |
|-------------|-----------------|
| Regular | Rp 50.000 |
| VIP | Rp 100.000 |
| VVIP | Rp 150.000 |

---

## Pilihan Extras

| Item | Harga |
|------|-------|
| Popcorn | Rp 25.000 |
| Minuman | Rp 15.000 |
| Combo Meal | Rp 35.000 |

> Total harga = (harga kursi × jumlah tiket) + harga extras

---

## Aturan Validasi Form

| Field | Aturan |
|-------|--------|
| **Nama** | Wajib diisi, minimal 3 karakter |
| **Email** | Wajib diisi, harus mengandung `@` dan `.` |
| **Nomor HP** | Wajib diisi, hanya angka, panjang 10–13 digit |
| **Jumlah Tiket** | Wajib diisi, angka antara 1–10 |
| **Film** | Wajib dipilih (bukan placeholder) |
| **Tanggal** | Wajib dipilih, tidak boleh tanggal lampau |
| **Jenis Kursi** | Wajib dipilih salah satu |
| **Jam Tayang** | Wajib dipilih salah satu |
| **Username** | Wajib diisi saat registrasi |
| **Password** | Minimal 4 karakter, harus cocok dengan konfirmasi |

---
