package com.example.bhargav.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowQuestionActivity extends AppCompatActivity {

    private String name,descp,question;
    private TextView tvname,tvdescp,tvques;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);

        tvname = (TextView)findViewById(R.id.tvname);
        tvdescp = (TextView)findViewById(R.id.tvdescp);
        tvques = (TextView)findViewById(R.id.tvquestion);
        name = getIntent().getStringExtra("Name");
        descp = getIntent().getStringExtra("Description");
        question = getIntent().getStringExtra("Question");

        tvname.setText(name);
        tvdescp.setText(descp);
        tvques.setText(question);

    }
}
