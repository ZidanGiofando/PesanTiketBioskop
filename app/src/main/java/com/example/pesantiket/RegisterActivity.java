package com.example.pesantiket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity untuk proses registrasi akun user
 */
public class RegisterActivity extends AppCompatActivity {

    // Deklarasi komponen input
    private EditText etUsername, etPassword, etConfirmPassword;

    // Tombol register
    private Button btnRegister;

    // SharedPreferences untuk menyimpan data user
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menghubungkan Activity dengan layout XML
        setContentView(R.layout.activity_register);

        // Menghubungkan variabel Java dengan ID di XML
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);

        // Membuat SharedPreferences
        sharedPreferences =
                getSharedPreferences("UserData", MODE_PRIVATE);

        // Event ketika tombol register ditekan
        btnRegister.setOnClickListener(v -> registerUser());
    }

    /**
     * Fungsi untuk melakukan registrasi user
     */
    private void registerUser() {

        // Mengambil input dari EditText
        String username =
                etUsername.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        String confirmPassword =
                etConfirmPassword.getText().toString().trim();

        // ==========================
        // VALIDASI USERNAME
        // ==========================

        if (TextUtils.isEmpty(username)) {

            etUsername.setError("Username wajib diisi");
            return;
        }

        // ==========================
        // VALIDASI PASSWORD
        // ==========================

        if (TextUtils.isEmpty(password)) {

            etPassword.setError("Password wajib diisi");
            return;
        }

        // Password minimal 4 karakter
        if (password.length() < 4) {

            etPassword.setError(
                    "Password minimal 4 karakter");

            return;
        }

        // ==========================
        // VALIDASI KONFIRMASI PASSWORD
        // ==========================

        // Memastikan password dan konfirmasi password sama
        if (!password.equals(confirmPassword)) {

            etConfirmPassword.setError(
                    "Password tidak sama");

            return;
        }

        // ==========================
        // SIMPAN DATA USER
        // ==========================

        SharedPreferences.Editor editor =
                sharedPreferences.edit();

        // Menyimpan username
        editor.putString("username", username);

        // Menyimpan password
        editor.putString("password", password);

        // Menyimpan perubahan
        editor.apply();

        // ==========================
        // MENAMPILKAN PESAN SUKSES
        // ==========================

        Toast.makeText(
                this,
                "Akun berhasil dibuat",
                Toast.LENGTH_SHORT
        ).show();

        // Menutup halaman RegisterActivity
        finish();
    }
}