package com.example.keepingscore.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.keepingscore.R;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TextView timetext;
    private TextView textView;
    private Button startbutton;
    private Button button;
    private TextView highscoretext;
    private SharedPreferences sharedPref2;
    private SharedPreferences.Editor editor2;
    private int currentScore;
    private int why;
    private int highscore;
    private Button resetbutton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        resetbutton = (Button) root.findViewById(R.id.reset);
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentScore = 0;
            }
        });
        final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        highscore = sharedPref.getInt(getString(R.string.saved_button_press_count_key), defaultValue);
        highscoretext = (TextView) root.findViewById(R.id.highscoretext);
        highscoretext.setText("High Score: " + Integer.toString(highscore));
        timetext = (TextView) root.findViewById(R.id.timetext);
        startbutton = (Button) root.findViewById(R.id.timestart);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // set current score to 0
                currentScore = 0;

                // start timer


            new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished){
                    timetext.setText("Seconds Left: " + millisUntilFinished / 1000);
                }
                public void onFinish(){

                    // check current score vs high score
                    // IF current score is greater than high score, write current score as NEw high score

                    timetext.setText("Game Over!");

                    if (currentScore > highscore){
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.saved_button_press_count_key), currentScore);

                        editor.commit();
                        highscore = currentScore;
                    }
                    currentScore = 0;
                    highscoretext.setText("High Score: " + Integer.toString(highscore));



                    //if () {
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putInt(getString(R.string.saved_button_press_count_key), CURRENTSCORE);
//                    editor.apply();
                    //}



                    // reset current score to 0
                   // final int currentScore = 0;

                    //highscoretext.setText("High Score: " + Integer.toString(highscore));
                }
            }.start();
            }
        });
        button = root.findViewById(R.id.game_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // add 1 to current score every time it's pressed
                currentScore++;


                textView.setText("Button has been pressed " + Integer.toString(currentScore) + " times!");
//                highscoretext.setText("High Score: " + Integer.toString(highscore));

            }
        });
        return root;
    }
}