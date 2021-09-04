package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;


public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText RegName_txt, RegEmail_txt, RegPassword_txt;
    private Button RegHome_btn, RegBack_btn, RegReg_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mAuth = FirebaseAuth.getInstance();

        RegName_txt = findViewById(R.id.RegName);
        RegEmail_txt = findViewById(R.id.RegEmail);
        RegPassword_txt = findViewById(R.id.RegEmail);

        RegHome_btn = findViewById(R.id.RegHome);
        RegHome_btn.setOnClickListener(this);
        RegBack_btn = findViewById(R.id.RegBack);
        RegBack_btn.setOnClickListener(this);
        RegReg_btn = findViewById(R.id.RegReg);
        RegReg_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.RegHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.RegBack:
                startActivity(new Intent(this, SecondActivity.class));
                break;
        }
    }
}