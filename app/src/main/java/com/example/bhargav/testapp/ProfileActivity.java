package com.example.bhargav.testapp;

import android.content.Intent;
import android.content.pm.ChangedPackages;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bhargav.testapp.Authentication.ChangespassActivity;
import com.example.bhargav.testapp.Authentication.LoginActivity;
import com.example.bhargav.testapp.Authentication.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView changepass,signout,stars,tvname,tvusn;
    private ImageButton leaderboard,profile,questions;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference db;
    private Long points;
    private String name,usn;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" DireHunt");
        toolbar.setLogo(R.drawable.logo_round);
        toolbar.setTitleTextColor(Color.parseColor("#FFD740"));

        stars = (TextView)findViewById(R.id.starnumber);
        leaderboard = (ImageButton)findViewById(R.id.leaderboard);
        questions = (ImageButton)findViewById(R.id.questions);
        profile = (ImageButton)findViewById(R.id.profile);
        tvname = (TextView)findViewById(R.id.tvname);
        tvusn = (TextView)findViewById(R.id.tvusn);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };


        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        assert uid!=null;

        db.child("users").child(uid).child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Point point = dataSnapshot.getValue(Point.class);
                points = point.getPoint();

                stars.setText(""+points);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        db.child("users").child(uid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Log.i("Hello",dataSnapshot.toString());
                    stages stage = dataSnapshot.getValue(stages.class);
                    name = stage.getName();

                    tvname.setText("" + name);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        db.child("users").child(uid).child("usn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Log.i("Hello",dataSnapshot.toString());
                    stages stage = dataSnapshot.getValue(stages.class);
                    usn = stage.getName();

                    tvusn.setText("" + usn);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,LeaderBoardActivity.class));
            }
        });
        questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(ProfileActivity.this,SettingsActivity.class));
                return true;


            default:


                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
