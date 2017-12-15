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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private Button login_btn;
    private EditText login_email;
    private EditText login_pass;
    private TextView NewMem;
    private TextView forgot;

    private FirebaseAuth Auth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Auth = FirebaseAuth.getInstance();
        if (Auth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), AccountInfo.class));
        }

        login_btn = (Button) findViewById(R.id.login_btn);
        login_email = (EditText) findViewById(R.id.login_email);
        login_pass = (EditText) findViewById(R.id.login_pass);
        NewMem = (TextView) findViewById(R.id.NewMem);
        forgot = (TextView) findViewById(R.id.forgot);

        progressDialog = new ProgressDialog(this);

        login_btn.setOnClickListener(this);
        forgot.setOnClickListener(this);
        NewMem.setOnClickListener(this);
    }
    private void userLogin(){
        String email = login_email.getText().toString().trim();
        String password  = login_pass.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        //logging in the user
        Auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful() == false){
                            //start the profile activity
                            progressDialog.cancel();
                            Toast.makeText(LogIn.this, "Please enter a valid email and password", Toast.LENGTH_LONG).show();

                        }
                        else{
                            finish();
                            startActivity(new Intent(getApplicationContext(), AccountInfo.class));
                        }
                    }
                });

    }

    public void onBackPressed() {
    Toast.makeText(getApplicationContext(), "Press Home Button for exiting" , Toast.LENGTH_SHORT).show();
       startActivity(new Intent(this, LogIn.class));
    }


    @Override
    public void onClick(View view) {
        if(view == login_btn){
            userLogin();
        }

        if(view == NewMem){
            finish();
            startActivity(new Intent(this, SignUp.class));
        }
        if(view == forgot){
            finish();
            startActivity(new Intent(this, Reset_Pass.class));

        }
    }
}