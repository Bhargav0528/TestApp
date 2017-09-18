package com.example.bhargav.testapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
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

    private Long points;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference db;
    private String status,hstatus;

    private String name,descp,answer,hint1,weekname,questionname;
    private TextView name1,desc1;
    private EditText answer1;
    private Button submit,bthint;

    private static final String TAG = "Questionac";

    ArrayList<Point> points1 ;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" DireHunt");
        toolbar.setLogo(R.drawable.logo_round);
        toolbar.setTitleTextColor(Color.parseColor("#FFD740"));

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference();


        name1 = (TextView)findViewById(R.id.name);
        desc1 = (TextView)findViewById(R.id.descp);
        answer1 = (EditText)findViewById(R.id.answer);
        submit = (Button)findViewById(R.id.submit);
        bthint = (Button)findViewById(R.id.bthint);

        name = getIntent().getStringExtra("name");
        descp=getIntent().getStringExtra("descp");
        answer = getIntent().getStringExtra("answer");
        hint1 = getIntent().getStringExtra("hint");
        weekname = getIntent().getStringExtra("weekname");
        questionname = getIntent().getStringExtra("questionname");
        status = getIntent().getStringExtra("status");
        hstatus = getIntent().getStringExtra("hstatus");

        Log.i(TAG,status);
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
                    Point point = dataSnapshot.getValue(Point.class);
                    Log.i(TAG, dataSnapshot.toString());
                    points1.add(point);


                points = points1.get(0).getPoint();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bthint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(QuestionActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.dialog_askhint,null);
                final Button btcancel = (Button)mview.findViewById(R.id.cancel);
                final Button btyes = (Button)mview.findViewById(R.id.yes);
                final TextView tvtext = (TextView)mview.findViewById(R.id.tvtext);
                final TextView hinthint = (TextView)mview.findViewById(R.id.hinthint);

                hinthint.setText(hint1);
                hinthint.setVisibility(View.GONE);

                mbuilder.setView(mview);
                final AlertDialog dialog = mbuilder.create();
                dialog.show();

                btcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvtext.setVisibility(View.GONE);

                        hinthint.setVisibility(View.VISIBLE);

                        if (hstatus.equals("no"))
                        {
                            db.child("users").child(uid).child("points").child("point").setValue(points - 1);
                            db.child("users").child(uid).child("week").child(weekname).child("questions").child(questionname).child("hintflag").setValue("yes");
                        }
                    }
                });
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer1.getText().toString().equals(answer))
                {

                    if (status.equals("no")) {
                        db.child("users").child(uid).child("points").child("point").setValue(points + 1);
                        db.child("users").child(uid).child("week").child(weekname).child("questions").child(questionname).child("pointflag").setValue("yes");
                    }
                    Toast.makeText(QuestionActivity.this,"Your Answer is Correct!",Toast.LENGTH_SHORT).show();

                      db.child("users").child(uid).child("week").child(weekname).child("questions").child(questionname).child("status").setValue("true");


                }
                else
                {
                    Toast.makeText(QuestionActivity.this,"Your Answer is Wrong!Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent t = new Intent(QuestionActivity.this,QuestionListActivity.class);
        t.putExtra("weekname",weekname);
        startActivity(t);
    }
}
