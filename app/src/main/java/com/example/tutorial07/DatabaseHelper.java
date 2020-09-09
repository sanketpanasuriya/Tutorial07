package com.example.tutorial07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tutorial07.db";
    public static final String TABLE_NAME = "student";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRST_NAME";
    public static final String COL_3 = "LAST_NAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";
    public static final String COL_6 = "BRANCH";
    public static final String COL_7 = "GENDER";
    public static final String COL_8 = "CITY";
    public static final String COL_9 = "STATUS";
    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COL_2+" TEXT ,"+COL_3+" TEXT ,"+COL_4+" TEXT unique ,"+COL_5+" TEXT ,"+COL_6+" TEXT ,"+COL_7+" TEXT ,"+COL_8+" TEXT ,"+COL_9+" TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String first_name, String last_name,String email, String password, String branch, String gender, String city, String status){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_2,first_name);
        values.put(COL_3,last_name);
        values.put(COL_4,email);
        values.put(COL_5,password);
        values.put(COL_6,branch);
        values.put(COL_7,gender);
        values.put(COL_8,city);
        values.put(COL_9,status);

        long result = db.insert(TABLE_NAME,null,values);
        return (result==-1)?false:true;
    }
    public boolean isValidUsername(String Username, String password){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COL_1};
        String selection = COL_4 + " = ?" + " AND " + COL_5 + " = ?";
        String[] selectionArgs = {Username,password};
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        if(cursor.getCount()==1){
            return true;
        }
        return false;
    }
}
