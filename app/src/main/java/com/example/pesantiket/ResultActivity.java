package com.example.pesantiket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Pemesanan");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Ambil semua data dari Intent
        Intent intent = getIntent();
        final String nama       = intent.getStringExtra("nama");
        final String email      = intent.getStringExtra("email");
        final String phone      = intent.getStringExtra("phone");
        final String film       = intent.getStringExtra("film");
        final String tanggal    = intent.getStringExtra("tanggal");
        final String jam        = intent.getStringExtra("jam");
        final String kursi      = intent.getStringExtra("kursi");
        final int hargaKursi    = intent.getIntExtra("harga_kursi", 0);
        final int jumlah        = intent.getIntExtra("jumlah", 1);
        final String extras     = intent.getStringExtra("extras");
        final int hargaExtra    = intent.getIntExtra("harga_extra", 0);
        final int total         = intent.getIntExtra("total", 0);

        // Format mata uang Rupiah
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        // Bangun teks ringkasan
        String ringkasan =
                "Nama Pemesan : " + nama + "\n" +
                        "Email        : " + email + "\n" +
                        "No. HP       : " + phone + "\n\n" +
                        "Film         : " + film + "\n" +
                        "Tanggal      : " + tanggal + "\n" +
                        "Jam          : " + jam + "\n" +
                        "Jenis Kursi  : " + kursi + " (" + rupiah.format(hargaKursi) + "/tiket)\n" +
                        "Jumlah Tiket : " + jumlah + " tiket\n\n" +
                        "Extras       : " + extras + "\n" +
                        "Biaya Extras : " + rupiah.format(hargaExtra) + "\n\n" +
                        "TOTAL BAYAR  : " + rupiah.format(total);

        final String shareText = "Saya baru pesan tiket bioskop!\n\n" + ringkasan +
                "\n\nDipesan via PesanTiket App";

        // Tampilkan ringkasan
        TextView tvResult = findViewById(R.id.tvResult);
        tvResult.setText(ringkasan);

        // ==========================================
        // IMPLICIT INTENT 1: Share via aplikasi lain
        // ==========================================
        Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Bagikan pesanan via..."));
        });

        Button btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + email));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                    "Konfirmasi Pemesanan Tiket - " + film);
            emailIntent.putExtra(Intent.EXTRA_TEXT,
                    "Halo " + nama + ",\n\nTerima kasih telah memesan tiket!\n\n" +
                            "DETAIL PEMESANAN:\n" + ringkasan +
                            "\n\nSampai jumpa di bioskop!\nTim PesanTiket");

            try {
                startActivity(emailIntent);
            } catch (android.content.ActivityNotFoundException e) {
                Toast.makeText(this, "Tidak ada aplikasi email yang terinstall",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // ==========================================
        // IMPLICIT INTENT 3: Telepon bioskop
        // ==========================================
        Button btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:02121234567"));
            startActivity(callIntent);
        });

        // ==========================================
        // IMPLICIT INTENT 4: Buka website bioskop
        // ==========================================
        Button btnWeb = findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(v -> {
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse("https://www.cgv.id"));
            startActivity(webIntent);
        });

        // Tombol Kembali (Explicit Intent - finish)
        Button btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
