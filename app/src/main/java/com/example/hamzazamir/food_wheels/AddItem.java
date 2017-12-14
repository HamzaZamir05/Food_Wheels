package com.example.hamzazamir.food_wheels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import static com.example.hamzazamir.food_wheels.R.id.add_item;
import static com.example.hamzazamir.food_wheels.R.id.item_name;
import static com.example.hamzazamir.food_wheels.R.id.item_price;
import static com.example.hamzazamir.food_wheels.R.id.item_quan;

public class AddItem extends AppCompatActivity implements View.OnClickListener{

    private Button add_item;
    private EditText item_name;
    private EditText item_desc;
    private EditText item_quan;
    private EditText item_price;

    private ProgressDialog progressDialog;
    private StorageReference mStorage;
    private FirebaseAuth Auth;
    private DatabaseReference mRef;
    private FirebaseDatabase db;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        //mRef = FirebaseDatabase.getInstance().getReference().child("Items");
        Auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();


        progressDialog = new ProgressDialog(this);

        item_name = (EditText) findViewById(R.id.item_name);
        item_desc = (EditText) findViewById(R.id.item_des);
        item_quan = (EditText) findViewById(R.id.item_quan);
        add_item = (Button) findViewById(R.id.add_item);
        item_price = (EditText) findViewById(R.id.item_price);

        add_item.setOnClickListener(this);
    }


    private void additem() {
        final String item_id = UUID.randomUUID().toString();
        final FirebaseUser user = Auth.getCurrentUser();
        String itemname = item_name.getText().toString().trim();
        String itemdes = item_desc.getText().toString().trim();
        String itemprice = item_price.getText().toString().trim();
        String itemquan = item_quan.getText().toString().trim();

        final ItemInformation info = new ItemInformation(itemname, itemdes, itemquan, itemprice, item_id);

        mRef = FirebaseDatabase.getInstance().getReference().child("Items").child(user.getUid());
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Items");
        mRef.push().child(item_id);
        mRef = FirebaseDatabase.getInstance().getReference().child("Items").child(user.getUid()).child(item_id);
        mRef.setValue(info);
        Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this,Menu_List.class));
    }


    @Override
    public void onClick(View v) {
        if (v == add_item) {
            additem();

        }
    }
    }
