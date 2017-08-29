package com.example.bhargav.testapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionsViewActivity extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference db;
    private ImageView week1,week2;
    private ArrayList<stages> stages;
    private ArrayList<questions> questions;
    private String uid;
    private ProgressDialog pDialog;
    private int stage;
    private static final String TAG = "questions";
    private TextView tv;
    private ImageView q1,q2,q3,q4,q5,q6;
    private Button btnextstage;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_view);

        q1 = (ImageView)findViewById(R.id.q1);
        q2 = (ImageView)findViewById(R.id.q2);
        q3 = (ImageView)findViewById(R.id.q3);
        q4 = (ImageView)findViewById(R.id.q4);
        q5 = (ImageView)findViewById(R.id.q5);
        q6 = (ImageView)findViewById(R.id.q6);

        btnextstage = (Button)findViewById(R.id.btnextstage);

        tv = (TextView)findViewById(R.id.tv);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        stages = new ArrayList<>();

        stage =0;

            FirebaseUser user = mAuth.getCurrentUser();

            assert  user!=null;
            uid = user.getUid();



            Log.i(TAG,"done");
            db.child("users").child(uid).child("stage").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot PostSnapshot : dataSnapshot.getChildren()) {
                        com.example.bhargav.testapp.stages stage = PostSnapshot.getValue(com.example.bhargav.testapp.stages.class);
                        Log.i(TAG, PostSnapshot.toString());
                        stages.add(stage);
                    }

                        for (int i = 0; i < stages.size(); i++) {

                            Log.i(TAG, stages.get(i).getValue().toString());
                            if (stages.get(i).getValue() == false) {
                                break;
                            } else
                                stage++;
                        }

                        Log.i(TAG, "" + stage);
                    db = FirebaseDatabase.getInstance().getReference();
                    questions = new ArrayList<>();

                    if (stages.get(1).getValue()==false)
                        stage =1;
                    db.child("stage").child("stage" + stage).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot PostSnapshot : dataSnapshot.getChildren()) {
                                Log.i(TAG, PostSnapshot.toString());
                                com.example.bhargav.testapp.questions ques = PostSnapshot.getValue(com.example.bhargav.testapp.questions.class);
                                questions.add(ques);
                            }
                            tv.setText(questions.get(0).getQuestion());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsViewActivity.this,ShowQuestionActivity.class);
                i.putExtra("Question", questions.get(0).getQuestion());
                i.putExtra("Name", questions.get(0).getName());
                i.putExtra("Description", questions.get(0).getDescription());
                startActivity(i);
            }
        });
        q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsViewActivity.this,ShowQuestionActivity.class);
                i.putExtra("Question", questions.get(0).getQuestion());
                i.putExtra("Name", questions.get(0).getName());
                i.putExtra("Description", questions.get(0).getDescription());
                startActivity(i);

            }
        });
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsViewActivity.this,ShowQuestionActivity.class);
                i.putExtra("Question", questions.get(1).getQuestion());
                i.putExtra("Name", questions.get(1).getName());
                i.putExtra("Description", questions.get(1).getDescription());
                startActivity(i);

            }
        });
        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsViewActivity.this,ShowQuestionActivity.class);
                i.putExtra("Question", questions.get(2).getQuestion());
                i.putExtra("Name", questions.get(2).getName());
                i.putExtra("Description", questions.get(2).getDescription());
                startActivity(i);

            }
        });
        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsViewActivity.this,ShowQuestionActivity.class);
                i.putExtra("Question", questions.get(3).getQuestion());
                i.putExtra("Name", questions.get(3).getName());
                i.putExtra("Description", questions.get(3).getDescription());
                startActivity(i);

            }
        });
        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsViewActivity.this,ShowQuestionActivity.class);
                i.putExtra("Question", questions.get(4).getQuestion());
                i.putExtra("Name", questions.get(4).getName());
                i.putExtra("Description", questions.get(4).getDescription());
                startActivity(i);

            }
        });
        q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionsViewActivity.this,ShowQuestionActivity.class);
                i.putExtra("Question", questions.get(5).getQuestion());
                i.putExtra("Name", questions.get(5).getName());
                i.putExtra("Description", questions.get(5).getDescription());
                startActivity(i);
            }
        });

        btnextstage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child("users").child(uid).child("stage").child("stage"+(stage+1)).child("value").setValue(true);
                startActivity(new Intent(QuestionsViewActivity.this,QuestionsViewActivity.class));
                finish();
            }
        });



    }


}
