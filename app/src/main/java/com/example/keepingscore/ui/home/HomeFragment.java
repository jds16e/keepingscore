package com.example.keepingscore.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.keepingscore.R;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

                Button button = root.findViewById(R.id.mash_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
                        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
                        int timesPressed = sharedPref.getInt(getString(R.string.saved_button_press_count_key), defaultValue);

                        int newTimespressed = timesPressed + 1;

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.saved_button_press_count_key), newTimespressed);
                        editor.apply();

                        textView.setText("Button has been pressed " + Integer.toString(newTimespressed) + " times!");


                    }
                });
        return  root;
    }
}