package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class userInfo extends AppCompatActivity {

    private String name, lname, degreeLevel, address, city, email, phoneN;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //declaring variables
        TextView name = findViewById(R.id.profession_card_name);
        TextView degree = findViewById(R.id.profession_card_degree);
        TextView address = findViewById(R.id.profession_card_address);
        TextView email = findViewById(R.id.profession_card_email);
        TextView phone = findViewById(R.id.profession_card_phone);
        TextView pay_rate = findViewById(R.id.profession_card_PayRate);


        int value_id = getIntent().getIntExtra("_id", -1);
        //Toast.makeText(userInfo.this, "User data not available      " + value , Toast.LENGTH_SHORT).show();

        dbManager db_manager = new dbManager(userInfo.this);
        SQLiteDatabase db = db_manager.getReadableDatabase();
        String[] projection = {"name", "Lname", "age", "degree_level", "City", "Address", "Email", "PhoneN"};
        String selection = "id = ?";
        String[] selectionArgs={String.valueOf(value_id)};
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        boolean proof = cursor.moveToFirst();


        String usr_name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String usr_Lname = cursor.getString(cursor.getColumnIndexOrThrow("Lname"));
        int usr_age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
        String usr_degree_level = cursor.getString(cursor.getColumnIndexOrThrow("degree_level"));
        String usr_city = cursor.getString(cursor.getColumnIndexOrThrow("City"));
        String usr_address = cursor.getString(cursor.getColumnIndexOrThrow("Address"));
        String usr_email = cursor.getString(cursor.getColumnIndexOrThrow("Email"));
        String usr_phoneN = cursor.getString(cursor.getColumnIndexOrThrow("PhoneN"));

        String name_statement = usr_name + " " + usr_Lname + ", " + usr_age;
        String location_statement = usr_address + ", " + usr_city;
        String phone_statement = "Phone number: " + usr_phoneN;
        String email_statement = "Email: " + usr_email;

        name.setText(name_statement);
        degree.setText(usr_degree_level);
        address.setText(location_statement);
        email.setText(email_statement);
        phone.setText(phone_statement);
        //name.setText(name_statement);

        //navigation
        BottomNavigationView BottomNavigation = findViewById(R.id.bottom_navigation);

        BottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_icon1) {
                    Intent intent = new Intent(userInfo.this, feed.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_icon2) {
                    Intent intent = new Intent(userInfo.this, browsing.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_icon3) {
                    Intent intent = new Intent(userInfo.this, userProfile.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}