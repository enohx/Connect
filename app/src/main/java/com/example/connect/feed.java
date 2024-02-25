package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class feed extends AppCompatActivity {

    public List<User> userList = new ArrayList<>(); // List to store user data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ListView lv = findViewById(R.id.lv);

        // Retrieve user data from the database and populate the userList
        dbManager db_manager = new dbManager(this);
        SQLiteDatabase db = db_manager.getReadableDatabase();

        String[] projection = {
                "name", "Lname", "age", "degree_level", "City", "Address", "Email", "PhoneN", "Username", "Password"
        };

        Cursor cursor = db.query("users", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String Lname = cursor.getString(cursor.getColumnIndexOrThrow("Lname"));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
            String degree_level = cursor.getString(cursor.getColumnIndexOrThrow("degree_level"));
            String city = cursor.getString(cursor.getColumnIndexOrThrow("City"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("Address"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("Email"));
            String phoneN = cursor.getString(cursor.getColumnIndexOrThrow("PhoneN"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("Password"));

            String fullname = name + " " + Lname;

            User user = new User(fullname, age, degree_level, city, address, email, phoneN, username, password);
            userList.add(user);
        }

        cursor.close();
        db.close();

        // Create an adapter to display the userList in the ListView
        ArrayAdapter<User> userAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.adapter_layout, R.id.card_fullName, userList);
        lv.setAdapter(userAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if (!userList.isEmpty()) {

                    Intent intent = new Intent(feed.this, userInfo.class);

                    intent.putExtra("_id", i + 1);
                    //Toast.makeText(feed.this, "............." + i, Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                } else {
                    // Handle the case when UserManager.userArrayList is empty or not populated
                    Toast.makeText(feed.this, "User data not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Navigation Bar
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_icon1) {
                    Intent intent = new Intent(feed.this, feed.class);
                    intent.putExtra("currentUserId", getIntent().getIntExtra("currentUserId", -1));
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_icon2) {
                    Intent intent = new Intent(feed.this, browsing.class);
                    intent.putExtra("currentUserId", getIntent().getIntExtra("currentUserId", -1));
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_icon3) {
                    Intent intent = new Intent(feed.this, userProfile.class);
                    intent.putExtra("currentUserId", getIntent().getIntExtra("currentUserId", -1));
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
