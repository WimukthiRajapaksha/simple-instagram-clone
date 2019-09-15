package com.example.instagramandroid02;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private FirebaseAuth mAuth;
    private String userName;
    private String password;
    Boolean signUpMode;
    FirebaseUser user;
    private String uid;
    EditText editUserName;
    EditText editPassword;
    TextView textLogin;
    Button btnSignUp;
    ImageView imageView;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            showUserList();
        }

        signUpMode = false;
        userName = "wimukthirajapaksha@gmail.com";
        password = "WimukthiRajapaksha";
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        btnSignUp = findViewById(R.id.buttonSignUp);
        imageView = findViewById(R.id.imageView);
        relativeLayout = findViewById(R.id.relativeLayout);

        imageView.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);

        textLogin = findViewById(R.id.textLogin);
        textLogin.setOnClickListener(this);
        editPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    onSignUpOrLogin(view);
                }
                return false;
            }
        });
        if (mAuth.getCurrentUser() != null) {
            Log.i("User--", mAuth.getCurrentUser().toString());
        } else {
            Log.i("User--", "Error");
        }

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            onSignUpOrLogin(view);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showUserList() {
        Intent i = new Intent(getApplicationContext(), UserList.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAuth(View view) {
        userName = editUserName.getText().toString();
        password = editPassword.getText().toString();
        Log.i("Here", userName);
        Log.i("Here", password);
        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("hereee", userName);
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void onDetails(View view) {
        Intent i = new Intent(this, UserDetailsActivity.class);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            i.putExtra("email", user.getEmail());
            i.putExtra("uid", user.getUid());
        } else {
            i.putExtra("email", "wimukthirajapaksha@gmail.com");
            i.putExtra("uid", "REDYC349werhgs3424SE");
        }
        startActivity(i);
    }

    public void onSignUpOrLogin(View view) {
        Log.i("Mode", signUpMode.toString());
        userName = editUserName.getText().toString();
        password = editPassword.getText().toString();
        mAuth.signOut();
        if (!signUpMode) {
            Toast.makeText(this, "Login", Toast.LENGTH_LONG).show();
            mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        showUserList();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, userName + " " + password, Toast.LENGTH_LONG).show();
            mAuth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Done..", Toast.LENGTH_LONG).show();
                        showUserList();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        Log.i("click", String.valueOf(view.getId()));
        if (view.getId() == R.id.textLogin) {
            signUpMode = !signUpMode;
            Log.i("Here==", "login clicked");
            if (!signUpMode) {
                textLogin.setText("Sign Up");
                btnSignUp.setText("Login");
            } else {
                btnSignUp.setText("Sign Up");
                textLogin.setText("Login");
            }
        } else if (view.getId() == R.id.imageView || view.getId() == R.id.relativeLayout) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}

//wimu@gmail.com
//wimu1234