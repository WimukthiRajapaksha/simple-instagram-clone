package com.example.instagramandroid02;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.core.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Trace;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class UserDetailsActivity extends AppCompatActivity implements View.OnKeyListener {

    DatabaseReference mDatabase;
    String uid;
    User user;
    String userName;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Intent i = getIntent();
        text = "Here is the text";

        userName = i.getStringExtra("username");
        uid = i.getStringExtra("uid");

        TextView textEmail = findViewById(R.id.textEmail);
        textEmail.setText(userName + "\n" + text);

        TextView textUid = findViewById(R.id.textPassword);
        textUid.setText(uid);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase myRef = FirebaseDatabase.getInstance();
        mDatabase = myRef.getReference();

        user = new User(userName, "12345678", text);
        mDatabase.child(uid).setValue(user);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

    public void onExtra(View view) {
        mDatabase.child(uid).child("text").setValue("Changed..");
    }
}
