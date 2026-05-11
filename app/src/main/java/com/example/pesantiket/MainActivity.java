package com.example.pesantiket;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Deklarasi semua komponen UI
    private EditText etNama, etEmail, etPhone, etJumlahTiket;
    private Spinner spinnerFilm;
    private RadioGroup rgKursi;
    private RadioGroup rgJam;
    private CheckBox cbPopcorn, cbMinuman, cbCombo;
    private TextView tvTanggal;
    private Button btnTanggal, btnPesan;
    private ImageView ivPoster;

    // Menyimpan tanggal yang dipilih user
    private String selectedDate = "";

    // SharedPreferences untuk menyimpan data terakhir
    private SharedPreferences sharedPreferences;

    // Daftar film yang tampil di Spinner
    private final String[] daftarFilm = {
            "-- Pilih Film --",
            "Avengers: Doomsday (2026)",
            "Mission Impossible 8 (2025)",
            "Minecraft Movie (2025)",
            "Jurassic World Rebirth (2025)",
            "Superman: Legacy (2025)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menghubungkan Activity dengan layout XML
        setContentView(R.layout.activity_main);

        // Mengubah judul ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("PesanTiket - Bioskop Online");
        }

        // Memanggil fungsi inisialisasi komponen
        initViews();

        // Mengatur isi Spinner film
        setupSpinner();

        // Mengatur DatePicker
        setupDatePicker();

        // Memuat data terakhir dari SharedPreferences
        loadLastData();

        // Menambahkan Fragment informasi film
        if (savedInstanceState == null) {
            MovieInfoFragment fragment = new MovieInfoFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        // Event tombol pesan
        btnPesan.setOnClickListener(v -> validateAndSubmit());
    }

    /**
     * Fungsi untuk menghubungkan variabel Java
     * dengan komponen yang ada di XML
     */
    private void initViews() {

        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etJumlahTiket = findViewById(R.id.etJumlahTiket);

        spinnerFilm = findViewById(R.id.spinnerFilm);

        rgKursi = findViewById(R.id.rgKursi);
        rgJam = findViewById(R.id.rgJam);

        cbPopcorn = findViewById(R.id.cbPopcorn);
        cbMinuman = findViewById(R.id.cbMinuman);
        cbCombo = findViewById(R.id.cbCombo);

        tvTanggal = findViewById(R.id.tvTanggal);

        btnTanggal = findViewById(R.id.btnTanggal);
        btnPesan = findViewById(R.id.btnPesan);

        ivPoster = findViewById(R.id.ivPoster);

        // Membuat SharedPreferences
        sharedPreferences = getSharedPreferences("PesanTiket", MODE_PRIVATE);
    }

    /**
     * Fungsi untuk menampilkan daftar film
     * ke dalam Spinner
     */
    private void setupSpinner() {

        // Membuat adapter Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                daftarFilm);

        // Layout dropdown Spinner
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        // Memasang adapter ke Spinner
        spinnerFilm.setAdapter(adapter);
    }

    /**
     * Fungsi untuk memilih tanggal tayang
     * menggunakan DatePickerDialog
     */
    private void setupDatePicker() {

        btnTanggal.setOnClickListener(v -> {

            // Mengambil tanggal saat ini
            Calendar cal = Calendar.getInstance();

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            // Membuat DatePickerDialog
            DatePickerDialog dialog = new DatePickerDialog(this,

                    (view, y, m, d) -> {

                        // Menyimpan tanggal yang dipilih
                        selectedDate = d + "/" + (m + 1) + "/" + y;

                        // Menampilkan tanggal ke TextView
                        tvTanggal.setText("Tanggal: " + selectedDate);

                    }, year, month, day);

            // Membatasi agar user tidak bisa memilih tanggal lampau
            dialog.getDatePicker()
                    .setMinDate(System.currentTimeMillis() - 1000);

            // Menampilkan dialog
            dialog.show();
        });
    }

    /**
     * Fungsi untuk mengambil data terakhir
     * yang tersimpan di SharedPreferences
     */
    private void loadLastData() {

        // Mengambil data dari SharedPreferences
        String lastNama =
                sharedPreferences.getString("last_nama", "");

        String lastEmail =
                sharedPreferences.getString("last_email", "");

        String lastPhone =
                sharedPreferences.getString("last_phone", "");

        // Menampilkan hint nama terakhir
        if (!lastNama.equals("")) {
            etNama.setHint(lastNama);
        }

        // Menampilkan hint email terakhir
        if (!lastEmail.equals("")) {
            etEmail.setHint(lastEmail);
        }

        // Menampilkan hint nomor HP terakhir
        if (!lastPhone.equals("")) {
            etPhone.setHint(lastPhone);
        }
    }

    /**
     * Fungsi utama untuk:
     * 1. Validasi input user
     * 2. Menghitung total harga
     * 3. Menampilkan dialog konfirmasi
     * 4. Mengirim data ke ResultActivity
     */
    private void validateAndSubmit() {

        // Mengambil input dari user
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String jumlahStr = etJumlahTiket.getText().toString().trim();

        // ==========================
        // VALIDASI NAMA
        // ==========================

        if (TextUtils.isEmpty(nama)) {

            etNama.setError("Nama tidak boleh kosong");
            etNama.requestFocus();
            return;
        }

        if (nama.length() < 3) {

            etNama.setError("Nama minimal 3 karakter");
            etNama.requestFocus();
            return;
        }

        // ==========================
        // VALIDASI EMAIL
        // ==========================

        if (TextUtils.isEmpty(email)) {

            etEmail.setError("Email tidak boleh kosong");
            etEmail.requestFocus();
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {

            etEmail.setError(
                    "Format email tidak valid");

            etEmail.requestFocus();
            return;
        }

        // ==========================
        // VALIDASI NOMOR HP
        // ==========================

        if (TextUtils.isEmpty(phone)) {

            etPhone.setError(
                    "Nomor HP tidak boleh kosong");

            etPhone.requestFocus();
            return;
        }

        if (!phone.matches("[0-9]+")) {

            etPhone.setError(
                    "Nomor HP harus berupa angka");

            etPhone.requestFocus();
            return;
        }

        if (phone.length() < 10 || phone.length() > 13) {

            etPhone.setError(
                    "Nomor HP harus 10-13 digit");

            etPhone.requestFocus();
            return;
        }

        // ==========================
        // VALIDASI JUMLAH TIKET
        // ==========================

        if (TextUtils.isEmpty(jumlahStr)) {

            etJumlahTiket.setError(
                    "Jumlah tiket tidak boleh kosong");

            etJumlahTiket.requestFocus();
            return;
        }

        int jumlah;

        try {

            jumlah = Integer.parseInt(jumlahStr);

            if (jumlah < 1 || jumlah > 10) {

                etJumlahTiket.setError(
                        "Jumlah tiket harus antara 1 - 10");

                etJumlahTiket.requestFocus();
                return;
            }

        } catch (NumberFormatException e) {

            etJumlahTiket.setError(
                    "Jumlah tiket harus berupa angka");

            etJumlahTiket.requestFocus();
            return;
        }

        // ==========================
        // VALIDASI PILIH FILM
        // ==========================

        if (spinnerFilm.getSelectedItemPosition() == 0) {

            Toast.makeText(this,
                    "Silakan pilih film terlebih dahulu",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        // ==========================
        // VALIDASI TANGGAL
        // ==========================

        if (TextUtils.isEmpty(selectedDate)) {

            Toast.makeText(this,
                    "Silakan pilih tanggal penayangan",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        // ==========================
        // VALIDASI KURSI
        // ==========================

        if (rgKursi.getCheckedRadioButtonId() == -1) {

            Toast.makeText(this,
                    "Silakan pilih jenis kursi",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        // ==========================
        // VALIDASI JAM TAYANG
        // ==========================

        if (rgJam.getCheckedRadioButtonId() == -1) {

            Toast.makeText(this,
                    "Silakan pilih jam tayang",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        // ==========================
        // MENGAMBIL DATA KURSI
        // ==========================

        RadioButton selectedRb =
                findViewById(rgKursi.getCheckedRadioButtonId());

        String kursi =
                selectedRb.getTag().toString();

        // Menentukan harga kursi
        int hargaKursi;

        switch (kursi) {

            case "VIP":
                hargaKursi = 100000;
                break;

            case "VVIP":
                hargaKursi = 150000;
                break;

            default:
                hargaKursi = 50000;
                break;
        }

        // ==========================
        // MENGAMBIL EXTRA SNACK
        // ==========================

        ArrayList<String> extrasList = new ArrayList<>();

        int hargaExtra = 0;

        if (cbPopcorn.isChecked()) {

            extrasList.add("Popcorn");
            hargaExtra += 25000;
        }

        if (cbMinuman.isChecked()) {

            extrasList.add("Minuman");
            hargaExtra += 15000;
        }

        if (cbCombo.isChecked()) {

            extrasList.add("Combo Meal");
            hargaExtra += 35000;
        }

        // ==========================
        // MEMBUAT STRING EXTRA
        // ==========================

        StringBuilder sbExtras = new StringBuilder();

        if (extrasList.isEmpty()) {

            sbExtras.append("Tidak ada");

        } else {

            for (int i = 0; i < extrasList.size(); i++) {

                sbExtras.append(extrasList.get(i));

                if (i < extrasList.size() - 1) {
                    sbExtras.append(", ");
                }
            }
        }

        // ==========================
        // HITUNG TOTAL HARGA
        // ==========================

        int totalHarga =
                (hargaKursi * jumlah) + hargaExtra;

        // Final variable untuk digunakan di lambda
        final int finalJumlah = jumlah;
        final int finalHargaKursi = hargaKursi;
        final int finalHargaExtra = hargaExtra;
        final int finalTotalHarga = totalHarga;

        final String finalFilm =
                spinnerFilm.getSelectedItem().toString();

        final String finalExtras =
                sbExtras.toString();

        // ==========================
        // SIMPAN DATA TERAKHIR
        // ==========================

        SharedPreferences.Editor editor =
                sharedPreferences.edit();

        editor.putString("last_nama", nama);
        editor.putString("last_email", email);
        editor.putString("last_phone", phone);

        editor.apply();

        // ==========================
        // FORMAT RUPIAH
        // ==========================

        java.text.NumberFormat rupiah =
                java.text.NumberFormat.getCurrencyInstance(
                        new java.util.Locale("id", "ID"));

        // ==========================
        // MENGAMBIL JAM TAYANG
        // ==========================

        RadioButton selectedJam =
                findViewById(rgJam.getCheckedRadioButtonId());

        String jamTayang =
                selectedJam.getText().toString();

        // ==========================
        // MEMBUAT PREVIEW PESANAN
        // ==========================

        String previewPesanan =

                "Film: " + finalFilm + "\n" +
                        "Tanggal: " + selectedDate + "\n" +
                        "Jenis Kursi: " + kursi + "\n" +
                        "Jumlah Tiket: " + finalJumlah + "\n" +
                        "Jam Tayang: " + jamTayang + "\n" +
                        "Extras: " + finalExtras + "\n\n" +
                        "TOTAL BAYAR: " +
                        rupiah.format(finalTotalHarga);

        // ==========================
        // ALERT DIALOG KONFIRMASI
        // ==========================

        new AlertDialog.Builder(this)

                .setTitle("Konfirmasi Pembayaran")

                .setMessage(previewPesanan)

                .setCancelable(false)

                .setPositiveButton("Konfirmasi",
                        (dialog, which) -> {

                            // Membuat Intent pindah ke ResultActivity
                            Intent intent =
                                    new Intent(this,
                                            ResultActivity.class);

                            // Mengirim semua data ke ResultActivity
                            intent.putExtra("nama", nama);
                            intent.putExtra("email", email);
                            intent.putExtra("phone", phone);

                            intent.putExtra("film", finalFilm);

                            intent.putExtra("tanggal",
                                    selectedDate);

                            intent.putExtra("jam",
                                    jamTayang);

                            intent.putExtra("kursi",
                                    kursi);

                            intent.putExtra("harga_kursi",
                                    finalHargaKursi);

                            intent.putExtra("jumlah",
                                    finalJumlah);

                            intent.putExtra("extras",
                                    sbExtras.toString());

                            intent.putExtra("harga_extra",
                                    finalHargaExtra);

                            intent.putExtra("total",
                                    finalTotalHarga);

                            // Menjalankan ResultActivity
                            startActivity(intent);
                        })

                .setNegativeButton("Batal",
                        (dialog, which) -> {

                            // Menutup dialog
                            dialog.dismiss();
                        })

                // Menampilkan dialog
                .show();
    }
}