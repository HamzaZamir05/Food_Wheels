package com.example.hamzazamir.food_wheels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_Pass extends AppCompatActivity implements View.OnClickListener{
    private EditText reset_email;
    private Button reset;
    private TextView remembered;
    private FirebaseAuth Auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__pass);
        reset_email = (EditText) findViewById(R.id.reset_email);
        reset = (Button) findViewById(R.id.reset);
        remembered = (TextView) findViewById(R.id.remembered);
        progressDialog= new ProgressDialog(this);
        Auth = FirebaseAuth.getInstance();
        reset.setOnClickListener(this);
        remembered.setOnClickListener(this);
    }
    private void reset(){
        String email = reset_email.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Sending reset Email..");
        progressDialog.show();
        Auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if( v == reset){
            reset();
        }
        if( v == remembered){
            startActivity(new Intent(this, LogIn.class));
        }
    }

        }
