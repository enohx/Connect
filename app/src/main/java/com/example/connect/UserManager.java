package com.example.connect;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserManager /*extends SQLiteOpenHelper*/ {
    static ArrayList<User> userArrayList=new ArrayList<User>();

    public UserManager(/*@Nullable Context context*/){
        //super(context, "UserDB", null, 1);
    }



    public void addUser(User u){
        userArrayList.add(u);
    }
    public void removeUser(int index){
        userArrayList.remove(index);
    }
    public void editUser(int index, User u) {
        userArrayList.set(index,u);
    }


    //downwards is from class example
    /*@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists User (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                     + "Name TEXT," +
                                                       "Lname TEXT," +
                                                       "Age INTEGER," +
                "degree_level TEXT," +
                "City TEXT," +
                "Address TEXT );");
    }*

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase openDb(){
        SQLiteDatabase sqLiteDatabase =  getWritableDatabase();
        return sqLiteDatabase;
    }

    public void addUser(SQLiteDatabase sqLiteDatabase, User user){
        ContentValues uservalues = new ContentValues();
        uservalues.put("name", user.name);
        uservalues.put("image", user.image);
        uservalues.put("description", user.description);
        sqLiteDatabase.insert("User", null, uservalues);
    }

    public Cursor getUsers(SQLiteDatabase sqLiteDatabase){
        Cursor cursor =  sqLiteDatabase.rawQuery("select * from User", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            @SuppressLint("Range") String name =  cursor.getString(cursor.getColumnIndex("name"));
            Log.v("usernames", name);
            cursor.moveToNext();
        }
        return cursor;
    }*/

}