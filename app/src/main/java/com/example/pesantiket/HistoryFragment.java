package com.example.pesantiket;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HistoryFragment extends Fragment {

    private TextView tvHistory;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_history,
                container,
                false);

        tvHistory = view.findViewById(R.id.tvHistory);

        SharedPreferences sp = requireActivity()
                .getSharedPreferences("PesanTiket", getContext().MODE_PRIVATE);

        String history = sp.getString("history_data",
                "Belum ada riwayat");

        tvHistory.setText(history);

        return view;
    }
}