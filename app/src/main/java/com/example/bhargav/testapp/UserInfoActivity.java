package com.example.bhargav.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfoActivity extends AppCompatActivity {

    private EditText etname,etusn;

    private ImageView fab;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        etname = (EditText) findViewById(R.id.etname);
        etusn = (EditText) findViewById(R.id.etusn);

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        fab = (ImageView)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etname.getText().toString()) || TextUtils.isEmpty(etusn.getText().toString())) {
                    Toast.makeText(UserInfoActivity.this, "Please enter the name and usn", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    assert uid != null;

                    String name = etname.getText().toString();
                    String usn = etusn.getText().toString();
                    db.child("users").child(uid).child("name").child("name").setValue(name);
                    db.child("users").child(uid).child("points").child("point").setValue(0);
                    db.child("users").child(uid).child("usn").child("name").setValue(usn);
                    startActivity(new Intent(UserInfoActivity.this, MainActivity.class));
                }
            }
        });


    }
}
