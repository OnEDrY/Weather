package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


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
        RegPassword_txt = findViewById(R.id.RegPassword);

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
            case R.id.RegReg:
                registerUser();
        }
    }

    private void registerUser() {
        String name = RegName_txt.getText().toString().trim();
        String email = RegEmail_txt.getText().toString().trim();
        String password = RegPassword_txt.getText().toString().trim();

        if(name.isEmpty()){
            RegName_txt.setError("Поле Имя пустое!");
            RegName_txt.requestFocus();
            return;
        }
        if(email.isEmpty()){
            RegEmail_txt.setError("Поле Email пустое");
            RegEmail_txt.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            RegEmail_txt.setError("Укажите корректный Email адресс!");
            RegEmail_txt.requestFocus();
            return;
        }
        if(password.isEmpty()){
            RegPassword_txt.setError("Поле Пароль пустое!");
            RegPassword_txt.requestFocus();
            return;
        }
        if(password.length() < 6){
            RegPassword_txt.setError("Пароль должен состоять минимум из 6 символов");
            RegPassword_txt.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(name, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(RegActivity.this, "Wasted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(RegActivity.this, "Wasted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}
