package com.example.hamzazamir.food_wheels;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Menu_List extends AppCompatActivity {
    private RecyclerView myAdsList;
    private DatabaseReference mRef;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__list);
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        myAdsList = (RecyclerView) findViewById(R.id.ads_list);
        myAdsList.setHasFixedSize(true);
        myAdsList.setLayoutManager(new LinearLayoutManager(this));
        mRef = FirebaseDatabase.getInstance().getReference().child("Items").child(user.getUid());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();

        FirebaseRecyclerAdapter<ItemInformation, AdInformationViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemInformation, Menu_List.AdInformationViewHolder>(
                ItemInformation.class,
                R.layout.custom_menu_view,
                Menu_List.AdInformationViewHolder.class,
                mRef

        ) {
            @Override
            protected void populateViewHolder(Menu_List.AdInformationViewHolder viewHolder, ItemInformation model, int position) {


                final String Itemid = getRef(position).getKey();

                viewHolder.setPname(model.getItemname());
                viewHolder.setPprice(model.getItemprice());
                viewHolder.setAdDate(model.getItemdes());

      //          viewHolder.setImage(getApplicationContext(), model.getImageUri());

                progressDialog.dismiss();
                viewHolder.mView.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleAdIntent = new Intent(Menu_List.this, Menu.class);
                        singleAdIntent.putExtra("Item_Id", Itemid);


                        startActivity(singleAdIntent);

                        //Toast.makeText(getApplicationContext(), mProductId, Toast.LENGTH_LONG).show();
                    }
                }));

            }



        };

        myAdsList.setAdapter(firebaseRecyclerAdapter);



    }

    public static class AdInformationViewHolder extends RecyclerView.ViewHolder{

        View mView;



        public AdInformationViewHolder(View itemView) {
            super(itemView);


            mView = itemView;
        }

        public void setPname(String pname){

            TextView prname = (TextView)mView.findViewById(R.id.r_ad_pName);

            prname.setText(pname);
        }
        public void setPprice(String pprice){
            TextView prprice = (TextView)mView.findViewById(R.id.r_ad_pPrice);

            prprice.setText("Rs. "+ pprice);

        }
        public void setAdDate(String adDate){

            TextView pdate = (TextView)mView.findViewById(R.id.r_ad_pTime);

            pdate.setText(adDate);
        }


        public void setImage(Context ctx, String img){

            ImageView image = (ImageView) mView.findViewById(R.id.viewAdImage);
            Picasso.with(ctx).load(img).into(image);




        }


    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
//        Toast.makeText(getApplicationContext(), "Back Pressed" , Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(this, AccountInfo.class));
    }
}
