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

/**
 * Activity untuk menampilkan
 * hasil detail pemesanan tiket bioskop
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menghubungkan Activity dengan layout XML
        setContentView(R.layout.activity_result);

        // Mengatur judul ActionBar
        if (getSupportActionBar() != null) {

            getSupportActionBar()
                    .setTitle("Detail Pemesanan");

            // Menampilkan tombol back di ActionBar
            getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
        }

        // ==========================================
        // MENGAMBIL DATA DARI INTENT
        // ==========================================

        Intent intent = getIntent();

        final String nama =
                intent.getStringExtra("nama");

        final String email =
                intent.getStringExtra("email");

        final String phone =
                intent.getStringExtra("phone");

        final String film =
                intent.getStringExtra("film");

        final String tanggal =
                intent.getStringExtra("tanggal");

        final String jam =
                intent.getStringExtra("jam");

        final String kursi =
                intent.getStringExtra("kursi");

        final int hargaKursi =
                intent.getIntExtra("harga_kursi", 0);

        final int jumlah =
                intent.getIntExtra("jumlah", 1);

        final String extras =
                intent.getStringExtra("extras");

        final int hargaExtra =
                intent.getIntExtra("harga_extra", 0);

        final int total =
                intent.getIntExtra("total", 0);

        // ==========================================
        // FORMAT MATA UANG RUPIAH
        // ==========================================

        NumberFormat rupiah =
                NumberFormat.getCurrencyInstance(
                        new Locale("id", "ID"));

        // ==========================================
        // MEMBUAT TEKS RINGKASAN PEMESANAN
        // ==========================================

        String ringkasan =

                "Nama Pemesan : " + nama + "\n" +
                        "Email        : " + email + "\n" +
                        "No. HP       : " + phone + "\n\n" +

                        "Film         : " + film + "\n" +
                        "Tanggal      : " + tanggal + "\n" +
                        "Jam          : " + jam + "\n" +

                        "Jenis Kursi  : " + kursi +
                        " (" + rupiah.format(hargaKursi) +
                        "/tiket)\n" +

                        "Jumlah Tiket : " + jumlah +
                        " tiket\n\n" +

                        "Extras       : " + extras + "\n" +

                        "Biaya Extras : " +
                        rupiah.format(hargaExtra) + "\n\n" +

                        "TOTAL BAYAR  : " +
                        rupiah.format(total);

        // ==========================================
        // TEKS UNTUK SHARE
        // ==========================================

        final String shareText =

                "Saya baru pesan tiket bioskop!\n\n" +

                        ringkasan +

                        "\n\nDipesan via PesanTiket App";

        // ==========================================
        // MENAMPILKAN HASIL KE TEXTVIEW
        // ==========================================

        TextView tvResult =
                findViewById(R.id.tvResult);

        tvResult.setText(ringkasan);

        // ==========================================
        // IMPLICIT INTENT:
        // SHARE KE APLIKASI LAIN
        // ==========================================

        Button btnShare =
                findViewById(R.id.btnShare);

        btnShare.setOnClickListener(v -> {

            // Membuat Intent share
            Intent shareIntent =
                    new Intent(Intent.ACTION_SEND);

            // Tipe data yang dikirim
            shareIntent.setType("text/plain");

            // Isi teks yang akan dishare
            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    shareText);

            // Menampilkan pilihan aplikasi share
            startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Bagikan pesanan via..."
                    )
            );
        });

        // ==========================================
        // IMPLICIT INTENT:
        // KIRIM EMAIL
        // ==========================================

        Button btnEmail =
                findViewById(R.id.btnEmail);

        btnEmail.setOnClickListener(v -> {
            Intent emailIntent =
                    new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(
                    Intent.EXTRA_EMAIL,
                    new String[]{email}
            );
            // Subject
            emailIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Konfirmasi Pemesanan Tiket - " + film
            );
            // Isi email
            emailIntent.putExtra(
                    Intent.EXTRA_TEXT,

                    "Halo " + nama + ",\n\n" +

                            "Terima kasih telah memesan tiket!\n\n" +

                            "DETAIL PEMESANAN:\n\n" +

                            ringkasan +

                            "\n\nSampai jumpa di bioskop!\n" +
                            "Tim PesanTiket"
            );
            emailIntent.setPackage("com.google.android.gm");
            try {
                startActivity(emailIntent);
            } catch (Exception e) {
                Toast.makeText(
                        this,
                        "Aplikasi Gmail tidak ditemukan",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // ==========================================
        // IMPLICIT INTENT:
        // TELEPON BIOSKOP
        // ==========================================

        Button btnCall =
                findViewById(R.id.btnCall);

        btnCall.setOnClickListener(v -> {

            // Membuat Intent telepon
            Intent callIntent =
                    new Intent(Intent.ACTION_DIAL);

            // Nomor telepon bioskop
            callIntent.setData(
                    Uri.parse("tel:02121234567"));

            // Membuka dial telepon
            startActivity(callIntent);
        });

        // ==========================================
        // IMPLICIT INTENT:
        // MEMBUKA WEBSITE BIOSKOP
        // ==========================================

        Button btnWeb =
                findViewById(R.id.btnWeb);

        btnWeb.setOnClickListener(v -> {

            // Membuat Intent membuka browser
            Intent webIntent =
                    new Intent(Intent.ACTION_VIEW);

            // URL website bioskop
            webIntent.setData(
                    Uri.parse("https://www.cgv.id"));

            // Membuka browser
            startActivity(webIntent);
        });

        // ==========================================
        // TOMBOL KEMBALI
        // ==========================================

        Button btnKembali =
                findViewById(R.id.btnKembali);

        btnKembali.setOnClickListener(v -> {

            // Menutup Activity
            finish();
        });
    }

    /**
     * Fungsi tombol back pada ActionBar
     */
    @Override
    public boolean onSupportNavigateUp() {

        // Menutup Activity
        finish();

        return true;
    }
}