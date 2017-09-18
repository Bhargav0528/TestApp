package com.example.bhargav.testapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bhargav.testapp.Authentication.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference db;
    private ProgressDialog pdialog;

    private ImageButton leaderboard,profile,questions;

    private static final String TAG = "MainActivity";

    //recyclerview
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //modelclass
    private List<ModelClass> models;
    ListView lv;
    EditText nameEditTxt, propTxt, descTxt;

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(" DireHunt");
        toolbar.setTitleTextColor(Color.parseColor("#FFD740"));
        toolbar.setLogo(R.drawable.logo_round);

        //getSupportActionBar().setCustomView(R.layout.custopm_action_bar_layout);
        //getSupportActionBar().setLogo(R.drawable.newlogo);
        //getSupportActionBar().setIcon(R.drawable.newlogo);

        leaderboard = (ImageButton)findViewById(R.id.leaderboard);
        questions = (ImageButton)findViewById(R.id.questions);
        profile = (ImageButton)findViewById(R.id.profile);
        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LeaderBoardActivity.class));
            }
        });
        questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        models = new ArrayList<>();
//try now ok
        //Ya it is showing the value see the  monitor
        db = FirebaseDatabase.getInstance().getReference();

        pdialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setMessage("Please Wait...");
        pdialog.show();
        db.child("week").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pdialog.dismiss();
                for (DataSnapshot Postsnapshot : dataSnapshot.getChildren())
                {

                    // Its not working,, It is not showing any value for datasnap shot
                    //so there is problem in ur firebase
                    //can u open and show me ur firebase structure

                    ModelClass model = Postsnapshot.getValue(ModelClass.class);
                    models.add(model);
                    Log.i(TAG,"Heloooooooo"+Postsnapshot.toString());

                    adapter.notifyDataSetChanged();
                }
                Log.i(TAG,"Heloooooooo"+models.size());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                    pdialog.dismiss();
            }
        });

        adapter = new CustomAdapter(MainActivity.this, models);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                 ModelClass model = models.get(position);
                Toast.makeText(getApplicationContext(), model.getName() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),QuestionListActivity.class);
                intent.putExtra("weekname",model.getName());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    //Settings button
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                return true;


            default:


                return super.onOptionsItemSelected(item);
        }
    }*/
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}