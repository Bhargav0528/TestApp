package com.example.bhargav.testapp;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    private int pointflag = 0;
    private int points;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference db;

    private String name,descp,answer,hint,weekname,questionname;
    private TextView name1,desc1;
    private EditText answer1;
    private Button submit;

    ArrayList<weekquestion> points1 ;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference();


        name1 = (TextView)findViewById(R.id.name);
        desc1 = (TextView)findViewById(R.id.descp);
        answer1 = (EditText)findViewById(R.id.answer);
        submit = (Button)findViewById(R.id.submit);

        name = getIntent().getStringExtra("name");
        descp=getIntent().getStringExtra("descp");
        answer = getIntent().getStringExtra("answer");
        hint = getIntent().getStringExtra("hint");
        weekname = getIntent().getStringExtra("weekname");
        questionname = getIntent().getStringExtra("questionname");

        name1.setText(name);
        desc1.setText(descp);
        Log.i("answers",answer+"users"+answer1+"given");

        FirebaseUser user = mAuth.getCurrentUser();

        uid = user.getUid();

        assert uid!=null;

        points1 = new ArrayList<>();
        db.child("users").child(uid).child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                weekquestion week = dataSnapshot.getValue(weekquestion.class);
                points1.add(week);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //points = points1.get(0).getPoints();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer1.getText().toString().equals(answer))
                {

                    Toast.makeText(QuestionActivity.this,"Your Answer is Correct!",Toast.LENGTH_SHORT).show();

                    if (pointflag==0) {
                        db.child("users").child(uid).child("week").child(weekname).child("questions").child(questionname).child("status").setValue("ANSWERED");
                        //db.child("users").child(uid).child("points").setValue(+1);
                    }
                    pointflag=1;
                }
                else
                {
                    Toast.makeText(QuestionActivity.this,"Your Answer is Wrong!Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
