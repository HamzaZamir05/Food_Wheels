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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
import com.google.firebase.database.DataSnapshot;

public class AccountInfo extends AppCompatActivity implements View.OnClickListener{
    private Button menu;
    private Button logout;
    private Button delete_acc;
    private Button update_btn , addItem;
    private EditText update_addr;
    private EditText update_type;
    private EditText update_name;
    private EditText update_email;
    private EditText update_cont;
    private EditText update_pass;
    private String userId;
    private ProgressDialog progressDialog;
    private String name;
    private String email;
    private String cont;
    private String addr;

    private FirebaseAuth Auth;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        Auth = FirebaseAuth.getInstance();
        if(Auth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LogIn.class));
        }
        FirebaseUser user = Auth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("USERS").child(user.getUid());

        progressDialog= new ProgressDialog(this);
        addItem = (Button) findViewById(R.id.addItem);
        logout = (Button)findViewById(R.id.logout);
        update_btn = (Button) findViewById(R.id.update_btn);
        update_addr = (EditText) findViewById(R.id.update_addr);
        update_name = (EditText) findViewById(R.id.update_name);
        update_email = (EditText) findViewById(R.id.update_email);
        delete_acc = (Button) findViewById(R.id.delete_acc);
        update_cont = (EditText) findViewById(R.id.update_cont);
        update_pass = (EditText) findViewById(R.id.update_pass);
        menu = (Button) findViewById(R.id.menu);
 //       update_type = (EditText) findViewById(R.id.update_type);
        logout.setOnClickListener(this);
        addItem.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_acc.setOnClickListener(this);
        menu.setOnClickListener(this);

        update_email.setText(user.getEmail());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map <String , String> map = (Map)dataSnapshot.getValue();
                String nameVal = map.get("name");
                String cont = map.get("contact");
                String addr = map.get("address");
                String type = map.get("type");
                if(type.equals("Volunteer") | type.equals("Welfare Organization")){

                    menu.setVisibility(View.INVISIBLE);
                    addItem.setVisibility(View.INVISIBLE);
                }
                update_cont.setText(cont);
                update_addr.setText(addr);
                update_name.setText(nameVal);
      //          update_type.setText(type);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void DeleteAcc(){
            mRef = FirebaseDatabase.getInstance().getReference().child("USERS");
            FirebaseUser user = Auth.getCurrentUser();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference(user.getUid()).child("USERS");
            user.delete();
            user = Auth.getCurrentUser();
            mRef.child(user.getUid()).setValue(null);
            DatabaseReference FB = FirebaseDatabase.getInstance().getReference().child("Items").child(user.getUid());
            FB.setValue(null);
            Auth.signOut();
            finish();
            startActivity(new Intent(this,LogIn.class));

    }
    private void Update(){
       final String email = update_email.getText().toString();
        final String name = update_name.getText().toString();
        final String pass = update_pass.getText().toString();
        String addr = update_addr.getText().toString();
        String cont = update_cont.getText().toString();

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

       final FirebaseUser user = Auth.getCurrentUser();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(user.getUid());

        userId = user.getUid();

        mRef.child("name").setValue(name);
        mRef.child("contact").setValue(cont);
        mRef.child("address").setValue(addr);

        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(user.getUid());
                            mRef.child("email").setValue(email);
                        } else {
                            Toast.makeText(getApplicationContext(), "Email Unchanged", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        user.updatePassword(pass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(user.getUid());
                        } else {
                            Toast.makeText(getApplicationContext(), "Password Unchanged", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        Toast.makeText(this,"Profile updated",Toast.LENGTH_LONG).show();
        return;


     }




    @Override
    public void onClick(View v) {
        if(v == addItem){
            startActivity(new Intent(this,AddItem.class));

        }
        if( v == logout){
            Auth.signOut();
            finish();
            startActivity(new Intent(this, LogIn.class));
        }
        if( v == update_btn){
            Update();
        }
        if( v == delete_acc) {
            DeleteAcc();
        }
        if(v == menu){
            finish();
            startActivity(new Intent(this, Menu_List.class));

           }

    }
}
