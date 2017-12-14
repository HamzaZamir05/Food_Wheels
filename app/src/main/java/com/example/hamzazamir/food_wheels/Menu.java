package com.example.hamzazamir.food_wheels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;
import java.util.UUID;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    private Button add_item;
    private Button update_item;
    private Button delete_item;
    private EditText item_name;
    private EditText item_desc;
    private EditText item_quan;
    private EditText item_price;
    String Itemid = null;


    private ProgressDialog progressDialog;
    private StorageReference mStorage;
    private FirebaseAuth Auth;
    private DatabaseReference mRef;
    private FirebaseDatabase db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Itemid = getIntent().getStringExtra("Item_Id");

        //mRef = FirebaseDatabase.getInstance().getReference().child("Items");
        Auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        user = Auth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        delete_item = (Button) findViewById(R.id.delete_item);
        update_item = (Button) findViewById(R.id.update_item);
        item_name = (EditText) findViewById(R.id.item_name);
        item_desc = (EditText) findViewById(R.id.item_des);
        item_quan = (EditText) findViewById(R.id.item_quan);
        add_item = (Button) findViewById(R.id.add_item);
        item_price = (EditText) findViewById(R.id.item_price);

        add_item.setOnClickListener(this);
        update_item.setOnClickListener(this);
        delete_item.setOnClickListener(this);
    }
    protected void onStart(){
        super.onStart();
        user = Auth.getCurrentUser();
        mRef =  FirebaseDatabase.getInstance().getReference().child("Items").child(user.getUid()).child(Itemid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map)dataSnapshot.getValue();



                String Iname = map.get("itemname");
                String Iprice = map.get("itemprice");
                String Ides = map.get("itemdes");
                String Iquan = map.get("itemquan");
                String Iid = map.get("Item_id");

                item_name.setText(Iname);
                item_price.setText(Iprice);
                item_quan.setText(Iquan);
                item_desc.setText(Ides);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        startActivity(new Intent(this,Menu_List.class));
    }
    private void updateitem(){

        String itemname = item_name.getText().toString().trim();
        String itemdes = item_desc.getText().toString().trim();
        String itemprice = item_price.getText().toString().trim();
        String itemquan = item_quan.getText().toString().trim();

        final ItemInformation info = new ItemInformation(itemname, itemdes, itemquan, itemprice, Itemid);

        mRef = FirebaseDatabase.getInstance().getReference().child("Items").child(user.getUid()).child(Itemid);
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Items");
        mRef.push().child(Itemid);
        mRef = FirebaseDatabase.getInstance().getReference().child("Items").child(user.getUid()).child(Itemid);
        mRef.setValue(info);
        Toast.makeText(getApplicationContext(), "Item Updated", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,Menu_List.class));
    }

    private void deleteitem(){
    FirebaseUser user = Auth.getCurrentUser();
    mRef = FirebaseDatabase.getInstance().getReference().child("Items").child(user.getUid()).child(Itemid);
    mRef.setValue(null);
        startActivity(new Intent(this,Menu_List.class));

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == add_item.getId()) {
            additem();
        }
        if(v.getId()== delete_item.getId()){
            deleteitem();
        }
        if(v.getId() == update_item.getId()){
            updateitem();
        }
    }
}
