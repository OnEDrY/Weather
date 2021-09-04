package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText AuthEmail_txt;
    private EditText AuthPassword_txt;

    private Button AuthHome_btn;
    private Button AuthReg_byn;
    private Button AuthAuth_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        DBHelper dbHelper;
        AuthEmail_txt = findViewById(R.id.AuthEmail);
        AuthPassword_txt = findViewById(R.id.AuthPassword);

        AuthHome_btn = findViewById(R.id.AuthHome);
        AuthReg_byn = findViewById(R.id.AuthReg);
        AuthAuth_btn = findViewById(R.id.AuthAuth);

        mAuth = FirebaseAuth.getInstance();

        AuthHome_btn.setOnClickListener(this);

        AuthReg_byn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.AuthHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.AuthReg:
                startActivity(new Intent(this, RegActivity.class));
                break;
        }
    }
}