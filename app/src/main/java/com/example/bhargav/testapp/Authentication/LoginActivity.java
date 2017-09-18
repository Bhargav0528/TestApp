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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhargav.testapp.MainActivity;
import com.example.bhargav.testapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btLogin;
    private TextView tvRegister;
    private TextView changepass;
    private EditText etEmail;
    private  EditText etPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar pb;

    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
          startActivity(new Intent(LoginActivity.this, MainActivity.class));
         finish();
        }
            setContentView(R.layout.activity_login);

            btLogin = (Button) findViewById(R.id.btLogin);
            tvRegister = (TextView) findViewById(R.id.tvregister);
            etEmail = (EditText) findViewById(R.id.etlogin);
            etPassword = (EditText) findViewById(R.id.etpassword);
            pb = (ProgressBar)findViewById(R.id.pb);
            pb.setVisibility(View.GONE);
            changepass = (TextView)findViewById(R.id.changepas);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepass.setVisibility(View.GONE);
                authenticate();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ChangespassActivity.class));
            }

        });

        }

        public void authenticate(){

            String email = etEmail.getText().toString().trim();
           final String password = etPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email))
            {
                Toast.makeText(LoginActivity.this,"Please enter your Email",Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password))
            {
                Toast.makeText(LoginActivity.this,"Please enter your Password",Toast.LENGTH_SHORT).show();
            }

            pb.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            pb.setVisibility(View.GONE);

                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    etPassword.setError("Password too short, enter minimum 6 characters!");
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                                    changepass.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        }
                    });
        }

    }

