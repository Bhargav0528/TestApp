package com.example.bhargav.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

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

    private static final String TAG = "MainActivity";

    //recyclerview
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //modelclass
    private List<ModelClass> models;
    ListView lv;
    EditText nameEditTxt, propTxt, descTxt;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        models = new ArrayList<>();
//try now ok
        //Ya it is showing the value see the  monitor
        db = FirebaseDatabase.getInstance().getReference();

        db.child("Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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

            }
        });

        adapter = new CustomAdapter(MainActivity.this, models);
        recyclerView.setAdapter(adapter);
    }

    //Settings button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                return true;


            default:


                return super.onOptionsItemSelected(item);
        }
    }
}