package com.example.bhargav.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LeaderBoardActivity extends AppCompatActivity {

    private ImageButton leaderboard,profile,questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        leaderboard = (ImageButton)findViewById(R.id.leaderboard);
        questions = (ImageButton)findViewById(R.id.questions);
        profile = (ImageButton)findViewById(R.id.profile);

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderBoardActivity.this,LeaderBoardActivity.class));
            }
        });
        questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderBoardActivity.this,MainActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderBoardActivity.this,ProfileActivity.class));
            }
        });
    }
}
