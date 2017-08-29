package com.example.bhargav.testapp.Authentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bhargav.testapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangespassActivity extends AppCompatActivity {

    private EditText etem;
    private Button btchange;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changespass);
        etem = (EditText)findViewById(R.id.etem);
        btchange = (Button)findViewById(R.id.change);



        btchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etem.getText().toString().trim();
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(ChangespassActivity.this,"Password sent to the registered Email",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(ChangespassActivity.this,"Failed to send Email",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
