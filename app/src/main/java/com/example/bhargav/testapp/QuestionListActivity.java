package com.example.bhargav.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity {


    private String name;
    private TextView tv,quesstatus;
    private ImageButton ques1,ques2,ques3;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference db;

    private ArrayList<weekquestion> weeks ;
    private ArrayList<weekquestion> userweeks;
    private static final String TAG = "Questionlist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        ques1 = (ImageButton)findViewById(R.id.question1);
        ques2 = (ImageButton)findViewById(R.id.question2);
        ques3 = (ImageButton)findViewById(R.id.question3);
        quesstatus = (TextView)findViewById(R.id.quesstatus);

        name = getIntent().getStringExtra("weekname");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        Log.i(TAG,uid);
        assert uid!=null;

        userweeks = new ArrayList<>();
        db.child("users").child(uid).child("week").child(name).child("questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot PostSnapshot : dataSnapshot.getChildren())
                {
                    weekquestion week = PostSnapshot.getValue(weekquestion.class);
                    userweeks.add(week);
                }
                quesstatus.setText(userweeks.get(0).getStatus());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        weeks = new ArrayList<>();
        db.child("week").child(name).child("questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot PostSnapshot : dataSnapshot.getChildren())
                {
                    weekquestion week = PostSnapshot.getValue(weekquestion.class);
                    weeks.add(week);
                    Log.i(TAG,PostSnapshot.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //tv.setText(name);

        ques1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionListActivity.this,QuestionActivity.class);
                i.putExtra("name",weeks.get(0).getName());
                i.putExtra("descp",weeks.get(0).getDescription());
                i.putExtra("answer",weeks.get(0).getAnswer());
                i.putExtra("hint",weeks.get(0).getHint());
                i.putExtra("weekname",name);
                i.putExtra("questionname","question1");
                startActivity(i);
            }
        });
        ques2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionListActivity.this,QuestionActivity.class);
                i.putExtra("name",weeks.get(1).getName());
                i.putExtra("descp",weeks.get(1).getDescription());
                i.putExtra("answer",weeks.get(1).getAnswer());
                i.putExtra("hint",weeks.get(1).getHint());
                i.putExtra("weekname",name);
                i.putExtra("questionname","question2");
                startActivity(i);

            }
        });
        ques3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionListActivity.this,QuestionActivity.class);
                i.putExtra("name",weeks.get(2).getName());
                i.putExtra("descp",weeks.get(2).getDescription());
                i.putExtra("answer",weeks.get(2).getAnswer());
                i.putExtra("hint",weeks.get(2).getHint());
                i.putExtra("weekname",name);
                i.putExtra("questionname","question3");
                startActivity(i);

            }
        });
    }
}
