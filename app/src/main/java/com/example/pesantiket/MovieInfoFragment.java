package com.example.pesantiket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment untuk menampilkan
 * informasi bioskop dan jadwal tayang
 */
public class MovieInfoFragment extends Fragment {

    /**
     * Fungsi yang dipanggil saat Fragment dibuat
     * untuk menampilkan layout fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Menghubungkan Fragment dengan layout XML
        View view = inflater.inflate(
                R.layout.fragment_movie_info,
                container,
                false);

        // Mengambil TextView dari layout
        TextView tvInfo =
                view.findViewById(R.id.tvMovieInfo);

        // Menampilkan informasi bioskop
        tvInfo.setText(
                "BIOSKOP CINEMAX JAKARTA\n" +
                        "Jl. Sudirman No. 1, Jakarta Selatan\n\n" +

                        "JAM TAYANG:\n" +
                        "10:00 | 13:00 | 16:00 | 19:00\n\n" +

                        "Hotline: (021) 2123-4567"
        );

        // Mengembalikan tampilan Fragment
        return view;
    }
}