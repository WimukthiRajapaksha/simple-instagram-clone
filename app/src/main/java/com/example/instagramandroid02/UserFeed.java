package com.example.instagramandroid02;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class UserFeed extends AppCompatActivity {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    List<Image> list;
    ArrayAdapter<Image> adapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        list = new ArrayList<Image>();
        adapter = new ArrayAdapter<Image>(this, android.R.layout.simple_list_item_1, list);
//        ListView listView = findViewById(R.id.listView);
//        listView.setAdapter(adapter);

        ImageView imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        String activeUserName = intent.getStringExtra("userName");
        Log.i("UserName user-feed", activeUserName);
        Log.i("Images", storageReference.toString());

        StorageReference sr = firebaseStorage.getReference(firebaseAuth.getCurrentUser().toString() + "/image0421fafc-06e5-4bac-be38-85b6acf7053c?alt=media&token=c65edf16-8b0f-4bbe-b84d-b46ab4203199.jpg" );
        Log.i("-----sto", firebaseStorage.getReference("com.google.firebase.auth.internal.zzn@c8de0ed/").toString());

        Log.i("----extr", String.valueOf(sr));
        Picasso.get()
                .load(String.valueOf(sr))
                .resize(500, 500)
                .centerCrop()
                .into(imageView);



        setTitle(activeUserName + "'s Neews Feed");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
