package com.example.bhargav.testapp.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bhargav.testapp.Constants;
import com.example.bhargav.testapp.MainActivity;
import com.example.bhargav.testapp.R;
import com.example.bhargav.testapp.UserInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btRegister;
    private ProgressBar pb;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mdatabase;
    private DatabaseReference ref;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();


        etEmail = (EditText) findViewById(R.id.etregister);
        etPassword = (EditText) findViewById(R.id.etregpassword);
        btRegister = (Button) findViewById(R.id.btregister);
        pb = (ProgressBar) findViewById(R.id.pb);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                }

                pb.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                pb.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    if (password.length() < 6) {
                                        Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    ref = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_USERS);
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    assert user!=null;
                                    String uid = user.getUid();


                                    ref.child(uid).child("Email").setValue(email);
                                    for(int i=1;i<11;i++) {
                                        for(int j=1;j<3;j++) {
                                            ref.child(uid).child("week").child("week" + i).child("questions").child("question"+j).child("hintflag").setValue("no");
                                            ref.child(uid).child("week").child("week" + i).child("questions").child("question"+j).child("status").setValue("false");
                                            ref.child(uid).child("week").child("week" + i).child("questions").child("question"+j).child("pointflag").setValue("no");
                                        }
                                    }
                                    startActivity(new Intent(RegisterActivity.this, UserInfoActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }


        @Override
        public void onStart () {
            super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
        }
        @Override
        public void onStop () {
            super.onStop();
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }


        @Override
        protected void onResume () {
            super.onResume();
            pb.setVisibility(View.GONE);
        }



}
