package com.example.android.dotsandboxes;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class game extends AppCompatActivity {
    View view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
    }

    public void threeSquare(View v){
        view=findViewById(R.id.game_view);
        view.setVisibility(View.GONE);

        view=findViewById(R.id.six_view);
        view.setVisibility(View.VISIBLE);

        view=findViewById(R.id.score_view);
        view.setVisibility(View.VISIBLE);
    }

    public void fourSquare(View v){
        view=findViewById(R.id.game_view);
        view.setVisibility(View.GONE);

        view=findViewById(R.id.four_view);
        view.setVisibility(View.VISIBLE);

        view=findViewById(R.id.score_view);
        view.setVisibility(View.VISIBLE);
    }

    public void fiveSquare(View v){
        view=findViewById(R.id.game_view);
        view.setVisibility(View.GONE);

        view=findViewById(R.id.five_view);
        view.setVisibility(View.VISIBLE);

        view=findViewById(R.id.score_view);
        view.setVisibility(View.VISIBLE);

    }
}
