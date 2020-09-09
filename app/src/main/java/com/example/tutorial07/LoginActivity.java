package com.example.tutorial07;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    private Button btnLogin;
    EditText Email;
    EditText Password;
    int count = 5;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email=(EditText)findViewById(R.id.edtEmail);

        myDB = new DatabaseHelper(this);

        Password=(EditText)findViewById(R.id.edtPassword);
        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnLogin = (Button)findViewById(R.id.btnLogin);

        if(sharedPreferences.contains("isLogin")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                checkDataEntered();
            }
        });

    }
    void checkDataEntered(){
        if(count==0){
            finish();
        }
        if(isEmpty(Email)) {
            Toast t = Toast.makeText(this,"You must enter Username",Toast.LENGTH_SHORT);
            t.show();
        }
        else if(isEmpty(Password)) {
            Toast t = Toast.makeText(this,"You must enter Password",Toast.LENGTH_SHORT);
            t.show();
        } else if(!isEmail(Email)){
            Email.setError("Enter valid email");
            Toast t = Toast.makeText(this,"Enter valid Email address",Toast.LENGTH_SHORT);
            t.show();
        }else if(myDB.isValidUsername(Email.getText().toString().trim(), Password.getText().toString().trim())==false){
            Toast t = Toast.makeText(this,"Please enter valid Username and Password, "+count+" attempts are left",Toast.LENGTH_SHORT);
            t.show();
            count--;
        } else{
            editor.putString("Username",Email.getText().toString());
            editor.putBoolean("isLogin",true);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public void new_register(View view) {
        Intent intent = new Intent(LoginActivity.this,Registration.class);
        startActivity(intent);
    }
}