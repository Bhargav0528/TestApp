package com.example.bhargav.testapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

public class QuestionListActivity extends AppCompatActivity {


    private String name;
    private TextView tv,quesstatus;
    private ImageView ques1,ques2,ques3;
    private ImageView ques1check,ques2check,ques3check;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference db;
    private Long pointflag;
    private ProgressDialog pdialog;

    private ArrayList<weekquestion> weeks ;
    private ArrayList<weekquestion> userweeks;
    private static final String TAG = "Questionlist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        String uri = "@drawable/tick_userinfo";
        String uri1 = "@drawable/xmark";

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" DireHunt");
        toolbar.setLogo(R.drawable.logo_round);
        toolbar.setTitleTextColor(Color.parseColor("#FFD740"));

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        int imageResource1 = getResources().getIdentifier(uri1, null, getPackageName());
        final Drawable res = getResources().getDrawable(imageResource);
        final Drawable res1 = getResources().getDrawable(imageResource1);


        ques1 = (ImageView)findViewById(R.id.question1);
        ques2 = (ImageView)findViewById(R.id.question2);
        //ques3 = (ImageButton)findViewById(R.id.question3);
        //quesstatus = (TextView)findViewById(R.id.quesstatus);

        ques1check = (ImageView)findViewById(R.id.ques1check);
        ques2check = (ImageView)findViewById(R.id.ques2check);
        //ques3check = (ImageView)findViewById(R.id.ques3check);

        name = getIntent().getStringExtra("weekname");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        Log.i(TAG,uid);
        assert uid!=null;

        pdialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.show();

        userweeks = new ArrayList<>();
        db.child("users").child(uid).child("week").child(name).child("questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot PostSnapshot : dataSnapshot.getChildren())
                {
                    weekquestion week = PostSnapshot.getValue(weekquestion.class);
                    userweeks.add(week);
                }
                //quesstatus.setText(userweeks.get(0).getStatus());
                if (userweeks.get(0).getStatus().equals("false"))
                ques1check.setImageDrawable(res1);
                else
                    ques1check.setImageDrawable(res);
                if (userweeks.get(1).getStatus().equals("false"))
                    ques2check.setImageDrawable(res1);
                else
                    ques2check.setImageDrawable(res);
                //if (userweeks.get(2).getStatus().equals("false"))
                   // ques3check.setImageDrawable(res1);
               // else
                //    ques3check.setImageDrawable(res);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        weeks = new ArrayList<>();
        db.child("week").child(name).child("questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pdialog.dismiss();

                for (DataSnapshot PostSnapshot : dataSnapshot.getChildren())
                {
                    weekquestion week = PostSnapshot.getValue(weekquestion.class);
                    weeks.add(week);
                    Log.i(TAG,PostSnapshot.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                pdialog.dismiss();
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
                i.putExtra("status", userweeks.get(0).getPointflag());
                i.putExtra("hstatus", userweeks.get(0).getHintflag());
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
                i.putExtra("status", userweeks.get(1).getPointflag());
                i.putExtra("hstatus", userweeks.get(1).getHintflag());
                startActivity(i);

            }
        });
        /*ques3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuestionListActivity.this,QuestionActivity.class);
                i.putExtra("name",weeks.get(2).getName());
                i.putExtra("descp",weeks.get(2).getDescription());
                i.putExtra("answer",weeks.get(2).getAnswer());
                i.putExtra("hint",weeks.get(2).getHint());
                i.putExtra("weekname",name);
                i.putExtra("questionname","question3");
                i.putExtra("status", userweeks.get(2).getPointflag());
                i.putExtra("hstatus", userweeks.get(2).getHintflag());
                startActivity(i);

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(QuestionListActivity.this,MainActivity.class));
    }
}
