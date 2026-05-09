# PesanTiket - Aplikasi Pemesanan Tiket Bioskop

## Identitas Mahasiswa
| Nama | NIM |
|------|-----|
| [Zidan Giofando] | [2411500834] |
| [Fayruz Azzuhri] | [2411500024] |

---

## Deskripsi Aplikasi

**PesanTiket** adalah aplikasi Android untuk pemesanan tiket bioskop secara online. Pengguna dapat memilih film, tanggal tayang, jenis kursi, dan tambahan makanan/minuman, kemudian menerima ringkasan pesanan yang bisa dibagikan.

---

## Fitur Aplikasi

### Wajib
| Fitur | Status |
|-------|--------|
| 2 Activity (MainActivity + ResultActivity) | ✅ |
| Explicit Intent (pindah antar Activity) | ✅ |
| Implicit Intent (Share, Email, Call, Browser) | ✅ |
| Form Validation (kosong, angka, email) | ✅ |
| EditText | ✅ |
| Spinner | ✅ |
| RadioButton | ✅ |
| CheckBox | ✅ |
| ImageView | ✅ |

### Bonus
| Fitur | Status |
|-------|--------|
| SharedPreferences (simpan data terakhir) | ✅ |
| Fragment (MovieInfoFragment) | ✅ |
| DatePickerDialog (pilih tanggal tayang) | ✅ |
| 4x Implicit Intent (Share, Email, Call, Web) | ✅ |

---

## Struktur Project

```
app/src/main/
├── java/com/example/pesantiket/
│   ├── MainActivity.java       ← Form pemesanan
│   ├── ResultActivity.java     ← Ringkasan & aksi lanjutan
│   └── MovieInfoFragment.java  ← Info bioskop (Fragment)
└── res/
    ├── layout/
    │   ├── activity_main.xml
    │   ├── activity_result.xml
    │   └── fragment_movie_info.xml
    ├── drawable/
    │   ├── ic_movie_banner.xml
    │   ├── edittext_background.xml
    │   └── spinner_background.xml
    └── values/
        ├── strings.xml
        ├── colors.xml
        └── themes.xml
```

---

## Alur Program

1. **MainActivity** → Pengguna mengisi form pemesanan tiket
2. Validasi form dilakukan saat tombol "PESAN SEKARANG" ditekan
3. Data disimpan ke **SharedPreferences** (nama, email, HP)
4. Berpindah ke **ResultActivity** via **Explicit Intent** membawa data pesanan
5. **ResultActivity** menampilkan ringkasan pesanan
6. Pengguna bisa melakukan aksi via **Implicit Intent**:
   - Share → berbagi ke aplikasi lain (WhatsApp, dll.)
   - Email → membuka app email dengan draft otomatis
   - Call → membuka dialer untuk menghubungi bioskop
   - Website → membuka browser ke website bioskop

---

## Screenshot

*(Tambahkan screenshot aplikasi di sini)*
