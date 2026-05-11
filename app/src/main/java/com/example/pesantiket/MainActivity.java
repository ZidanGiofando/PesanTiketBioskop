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

    private EditText etNama, etEmail, etPhone, etJumlahTiket;
    private Spinner spinnerFilm;
    private RadioGroup rgKursi;
    private RadioGroup rgJam;
    private CheckBox cbPopcorn, cbMinuman, cbCombo;
    private TextView tvTanggal;
    private Button btnTanggal, btnPesan, btnHistory;
    private ImageView ivPoster;

    private String selectedDate = "";
    private SharedPreferences sharedPreferences;

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
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("PesanTiket - Bioskop Online");
        }

        initViews();
        setupSpinner();
        setupDatePicker();
        loadLastData();

        // Tambahkan Fragment
        if (savedInstanceState == null) {
            MovieInfoFragment fragment = new MovieInfoFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        btnPesan.setOnClickListener(v -> validateAndSubmit());

        btnHistory.setOnClickListener(v -> {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer,
                            new HistoryFragment())
                    .commit();

        });
    }

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
        btnHistory = findViewById(R.id.btnHistory);

        sharedPreferences = getSharedPreferences("PesanTiket", MODE_PRIVATE);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                daftarFilm);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilm.setAdapter(adapter);
    }

    private void setupDatePicker() {
        btnTanggal.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this,
                    (view, y, m, d) -> {
                        selectedDate = d + "/" + (m + 1) + "/" + y;
                        tvTanggal.setText("Tanggal: " + selectedDate);
                    }, year, month, day);

            // Hanya boleh pilih tanggal hari ini ke depan
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        });
    }

    private void loadLastData() {

        String lastNama = sharedPreferences.getString("last_nama", "");
        String lastEmail = sharedPreferences.getString("last_email", "");
        String lastPhone = sharedPreferences.getString("last_phone", "");

        if (!lastNama.equals("")) {
            etNama.setHint(lastNama);
        }

        if (!lastEmail.equals("")) {
            etEmail.setHint(lastEmail);
        }

        if (!lastPhone.equals("")) {
            etPhone.setHint(lastPhone);
        }
    }

    private void validateAndSubmit() {
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String jumlahStr = etJumlahTiket.getText().toString().trim();

        // Validasi Nama
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

        // Validasi Email
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email tidak boleh kosong");
            etEmail.requestFocus();
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            etEmail.setError("Format email tidak valid (harus mengandung '@' dan '.')");
            etEmail.requestFocus();
            return;
        }

        // Validasi Nomor HP
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Nomor HP tidak boleh kosong");
            etPhone.requestFocus();
            return;
        }
        if (!phone.matches("[0-9]+")) {
            etPhone.setError("Nomor HP harus berupa angka");
            etPhone.requestFocus();
            return;
        }
        if (phone.length() < 10 || phone.length() > 13) {
            etPhone.setError("Nomor HP harus 10-13 digit");
            etPhone.requestFocus();
            return;
        }

        // Validasi Jumlah Tiket
        if (TextUtils.isEmpty(jumlahStr)) {
            etJumlahTiket.setError("Jumlah tiket tidak boleh kosong");
            etJumlahTiket.requestFocus();
            return;
        }
        int jumlah;
        try {
            jumlah = Integer.parseInt(jumlahStr);
            if (jumlah < 1 || jumlah > 10) {
                etJumlahTiket.setError("Jumlah tiket harus antara 1 - 10");
                etJumlahTiket.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etJumlahTiket.setError("Jumlah tiket harus berupa angka");
            etJumlahTiket.requestFocus();
            return;
        }

        // Validasi Pilih Film
        if (spinnerFilm.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Silakan pilih film terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Tanggal
        if (TextUtils.isEmpty(selectedDate)) {
            Toast.makeText(this, "Silakan pilih tanggal penayangan", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Jenis Kursi
        if (rgKursi.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Silakan pilih jenis kursi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rgJam.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Silakan pilih jam tayang", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ambil data kursi yang dipilih
        RadioButton selectedRb = findViewById(rgKursi.getCheckedRadioButtonId());
        String kursi = selectedRb.getTag().toString(); // "Regular", "VIP", "VVIP"
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
                break; // Regular
        }

        // Ambil extras
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

        // Build extras string
        StringBuilder sbExtras = new StringBuilder();
        if (extrasList.isEmpty()) {
            sbExtras.append("Tidak ada");
        } else {
            for (int i = 0; i < extrasList.size(); i++) {
                sbExtras.append(extrasList.get(i));
                if (i < extrasList.size() - 1) sbExtras.append(", ");
            }
        }

        // Hitung total
        int totalHarga = (hargaKursi * jumlah) + hargaExtra;

        final int finalJumlah = jumlah;
        final int finalHargaKursi = hargaKursi;
        final int finalHargaExtra = hargaExtra;
        final int finalTotalHarga = totalHarga;

        final String finalFilm = spinnerFilm.getSelectedItem().toString();
        final String finalExtras = sbExtras.toString();

        // Simpan ke SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_nama", nama);
        editor.putString("last_email", email);
        editor.putString("last_phone", phone);
        editor.apply();

        // Kirim data ke ResultActivity via Explicit Intent
        String film = spinnerFilm.getSelectedItem().toString();

        // Format Rupiah
        java.text.NumberFormat rupiah =
                java.text.NumberFormat.getCurrencyInstance(
                        new java.util.Locale("id", "ID"));

        // Jam
        RadioButton selectedJam =
                findViewById(rgJam.getCheckedRadioButtonId());

        String jamTayang = selectedJam.getText().toString();

        String previewPesanan =
                "Film: " + film + "\n" +
                        "Tanggal: " + selectedDate + "\n" +
                        "Jenis Kursi: " + kursi + "\n" +
                        "Jumlah Tiket: " + finalJumlah + "\n" +
                        "Jam Tayang  : " + jamTayang + "\n" +
                        "Extras: " + finalExtras + "\n\n" +
                        "TOTAL BAYAR: " + rupiah.format(finalTotalHarga);


        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Pembayaran")
                .setMessage(previewPesanan)
                .setCancelable(false)

                .setPositiveButton("Konfirmasi", (dialog, which) -> {

                    // History
                    String riwayatBaru =
                            "🎬 " + finalFilm + "\n" +
                                    "📅 " + selectedDate + "\n" +
                                    "🕒 " + jamTayang + "\n" +
                                    "💺 " + kursi + "\n" +
                                    "🎟️ " + finalJumlah + " tiket\n\n";

                    String historyLama =
                            sharedPreferences.getString("history_data", "");

                    String gabung = riwayatBaru + historyLama;

                    // Batas maksimal 3 history
                    String[] splitHistory = gabung.split("\n\n");

                    StringBuilder limitedHistory = new StringBuilder();

                    for (int i = 0; i < splitHistory.length && i < 3; i++) {
                        limitedHistory.append(splitHistory[i])
                                .append("\n\n");
                    }

                    sharedPreferences.edit()
                    .putString("history_data", limitedHistory.toString())
                    .apply();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer,
                                    new HistoryFragment())
                            .commit();

                    // Kirim data ke ResultActivity
                    Intent intent = new Intent(this, ResultActivity.class);

                    intent.putExtra("nama", nama);
                    intent.putExtra("email", email);
                    intent.putExtra("phone", phone);
                    intent.putExtra("film", finalFilm);
                    intent.putExtra("tanggal", selectedDate);
                    intent.putExtra("jam", jamTayang);
                    intent.putExtra("kursi", kursi);
                    intent.putExtra("harga_kursi", finalHargaKursi);
                    intent.putExtra("jumlah", finalJumlah);
                    intent.putExtra("extras", sbExtras.toString());
                    intent.putExtra("harga_extra", finalHargaExtra);
                    intent.putExtra("total", finalTotalHarga);

                    startActivity(intent);
                })

                .setNegativeButton("Batal", (dialog, which) -> {
                    dialog.dismiss();
                })

                .show();

    }
}
