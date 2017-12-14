package com.example.hamzazamir.food_wheels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button signup_btn;
    private EditText signup_name;
    private EditText signup_contact;
    private EditText signup_address;
    private EditText signup_password;
    private EditText signup_email;
    private EditText signup_repass;
    private TextView SignedUp;
    private Spinner Type;

    private FirebaseAuth Auth;
    private FirebaseDatabase database;
    private DatabaseReference mRef;

    private ProgressDialog progressDialog;
    private static final String TAG = SignUp.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        if (Auth.getCurrentUser() != null) {
        finish();
            //Toast.makeText(this, "Already SignedUp", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AccountInfo.class));
            //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
       }

        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_pass);
        signup_repass = (EditText) findViewById(R.id.signup_repass);
        signup_address = (EditText) findViewById(R.id.signup_address);
        signup_name = (EditText) findViewById(R.id.signup_name);
        signup_contact = (EditText) findViewById(R.id.signup_contact);
        signup_btn = (Button) findViewById(R.id.signup_btn);
        SignedUp = (TextView) findViewById(R.id.signedup);
        Type = (Spinner) findViewById(R.id.spinner);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        signup_btn.setOnClickListener(this);
        SignedUp.setOnClickListener(this);

    }

    private void SaveInfo() {
        mRef = database.getReference().child("USERS");
        String name = signup_name.getText().toString().trim();
        String addr = signup_address.getText().toString().trim();
        String cont = signup_contact.getText().toString().trim();
        String email = signup_email.getText().toString().trim();
        String type = Type.getSelectedItem().toString().trim();

        FirebaseUser user = Auth.getCurrentUser();
        UserData userData = new UserData(name, email,cont, addr, type);
        mRef.child(user.getUid()).setValue(userData);
    }

    private void registerUser() {

        //getting email and password from edit texts
        String name = signup_name.getText().toString().trim();
        String addr = signup_address.getText().toString().trim();
        String pass = signup_password.getText().toString().trim();
        String repass = signup_repass.getText().toString().trim();
        String cont = signup_contact.getText().toString().trim();
        String email = signup_email.getText().toString().trim();
        String type = Type.getSelectedItem().toString();

        //checks
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {

                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();

                return;
            }
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(cont)) {

            Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(addr)) {

            Toast.makeText(this, "Enter You current address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {

            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(repass)) {

            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(repass)) {
            Toast.makeText(SignUp.this, "Password Doesn't match", Toast.LENGTH_SHORT).show();
            return;

        }

        //if the email and password are not empty
        //displaying a progress dialog
        else {

            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

        }
        mRef = database.getReference("USERS");


        //creating a new user
        Auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();

                            }

                        }

                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(SignUp.this, "Successfully registered", Toast.LENGTH_LONG).show();
                           SaveInfo();
                            startActivity(new Intent(getApplicationContext(), LogIn.class));
                            signup_email.setText(null);
                            signup_name.setText(null);
                            signup_contact.setText(null);
                            signup_address.setText(null);
                            signup_password.setText(null);
                            signup_repass.setText(null);
                        } else {

                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            Toast.makeText(SignUp.this, "An error occured while registering!!",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v == signup_btn) {
            registerUser();
        }
        if (v == SignedUp){
            startActivity(new Intent(this, LogIn.class));
        }

    }
}

