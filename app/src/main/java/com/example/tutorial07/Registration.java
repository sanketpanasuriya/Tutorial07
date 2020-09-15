package com.example.tutorial07;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {
    DatabaseHelper myDB;
    Button btnRegister;
    EditText fname,lname,uname,Password;
    Switch Branch;
    RadioGroup Gender;
    RadioButton selectedGender;
    Spinner City;
    CheckBox Status;
    int genderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        myDB = new DatabaseHelper(this);

        fname = findViewById(R.id.edtFirstname);
        lname = findViewById(R.id.edtLastname);
        uname = findViewById(R.id.edtUsername);
        Password = findViewById(R.id.edtPassword);
        Branch = findViewById(R.id.switchBranch);
        Gender = (RadioGroup) findViewById(R.id.genderButton);

        City = findViewById(R.id.spinner);
        Status = findViewById(R.id.checkBox);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });
    }
     void checkDataEntered(){
        String firstname,lastname,username,password,branch,gender,city,status;
        int count=0;
        genderID = Gender.getCheckedRadioButtonId();
        selectedGender = findViewById(genderID);
        firstname = fname.getText().toString();
        lastname = lname.getText().toString();
        username = uname.getText().toString();
        password = Password.getText().toString();
        branch = !Branch.isChecked()?"CE":"IT";
        gender = selectedGender.getText().toString();
        city = City.getSelectedItem().toString();
        status = Status.isChecked()?"Active":"Inactive";

        if(isEmpty(fname)){
            Toast t = Toast.makeText(this,"You must enter Firstname",Toast.LENGTH_SHORT);
            t.show();
            count++;
            return;
        }
        if(isEmpty(lname)){
            Toast t = Toast.makeText(this,"You must enter Lastname",Toast.LENGTH_SHORT);
            t.show();
            count++;
            return;
        }
        if(isEmpty(uname)){
            Toast t = Toast.makeText(this,"You must enter email as Username",Toast.LENGTH_SHORT);
            t.show();
            count++;
            return;
        }
        if(isEmpty(Password)){
            Toast t = Toast.makeText(this,"You must enter password",Toast.LENGTH_SHORT);
            t.show();
            count++;
            return;
        }
        if(city.equals("Select City")){
            Toast t = Toast.makeText(this,"You must select city",Toast.LENGTH_SHORT);
            t.show();
            count++;
            return;
        }
        if(password.length() < 8){
            Toast.makeText(this,"Password must be greater then 8",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isEmail(uname)){
            uname.setError("Please Enter valide email");
            Toast t = Toast.makeText(this,"Please Enter valid Email",Toast.LENGTH_SHORT);
            t.show();
            return;
        }else if(count==0){
            if(myDB.insertData(firstname,lastname,username,password,branch,gender,city,status)) {
                Toast t = Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_LONG);
                t.show();
                Intent intent = new Intent(Registration.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast t = Toast.makeText(this,"Username is already exists",Toast.LENGTH_SHORT);
                t.show();
            }
        }
     }
     boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
     }
     boolean isEmail(EditText text){
        CharSequence str = text.getText().toString();
        return (Patterns.EMAIL_ADDRESS.matcher(str).matches());
     }
}