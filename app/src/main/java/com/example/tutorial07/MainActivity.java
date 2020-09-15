package com.example.tutorial07;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    TextView lblWelcome;
    TextView btnBackgroundColorChange;
    String userName ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConstraintLayout lytWelcome;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("Login",Context.MODE_PRIVATE);

        lblWelcome = findViewById(R.id.lblWelcome);
        btnBackgroundColorChange = findViewById(R.id.btnBackgroundColorChange);
        registerForContextMenu(btnBackgroundColorChange);

        userName = sharedPreferences.getString("Username","");
        editor = sharedPreferences.edit();
        lytWelcome = findViewById(R.id.lytWelcome);
        lblWelcome.setText(lblWelcome.getText().toString()+", "+userName);

        if(sharedPreferences.contains("WelcomeBackGround")){
            BackGround(sharedPreferences.getInt("WelcomeBackGround",0));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_manu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuLogout:
                mnu_onClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.custom_context_manu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnuBlue:
                BackGround(R.color.colorBlue);
                break;
            case R.id.mnuRed:
                BackGround(R.color.colorRed);
                break;
            case R.id.mnuGreen:
                BackGround(R.color.colorGreen);
                break;
            case R.id.mnuDefault:
                editor.remove("WelcomeBackGround");
                editor.commit();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                //BackGround(sharedPreferences.getInt("WelcomeBackGround",0));
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void BackGround(int Color) {
        lytWelcome.setBackgroundColor(getResources().getColor(Color));
        editor.putInt("WelcomeBackGround",Color);
        editor.commit();
    }

    public void mnu_onClick(){
        new AlertDialog.Builder(this).setTitle("Logout")
                .setCancelable(false)
                .setMessage("Do you want to logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.remove("isLogin");
                        editor.remove("Username");
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Logout successfully",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Stay Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
        //AlertDialog alert = builder.create();
        //alert.setTitle("Logout");
        //alert.show();


    }

}