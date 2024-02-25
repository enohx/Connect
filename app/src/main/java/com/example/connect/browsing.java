package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class browsing extends AppCompatActivity {

    public List<User> userList = new ArrayList<>(); // List to store all users
    public ArrayAdapter<User> userAdapter; // Adapter for the ListView


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsing);

        SearchView sv = findViewById(R.id.searchView);
        SearchView s_degreeLv = findViewById(R.id.search_degree_level);
        s_degreeLv.setIconified(false);
        Button srtButton = findViewById(R.id.search_pay_rate);
        SearchView s_city = findViewById(R.id.search_city);
        s_city.setIconified(false);

        ListView lv = findViewById(R.id.lv1);

        // Retrieve user data from the database and populate the userList
        dbManager db_manager = new dbManager(this);
        SQLiteDatabase db = db_manager.getReadableDatabase();

        String[] projection = {
                "id","name", "Lname", "age", "degree_level", "City", "Address", "Email", "PhoneN", "Username", "Password"
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

        // Create an adapter with the userList to display all users in the ListView
        userAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.adapter_layout_2, R.id.card_fullName, userList);
        lv.setAdapter(userAdapter);

        // Set the search query listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = newText.toLowerCase();

                List<User> filteredList = new ArrayList<>();
                for (User user : userList) {
                    String fullName = user.Get_fullname().toLowerCase();
                    if (fullName.contains(query)) {
                        filteredList.add(user);
                    }
                }

                ArrayAdapter<User> userAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.adapter_layout_2, R.id.card_fullName, filteredList);
                lv.setAdapter(userAdapter);

                return true;
            }
        });

        s_degreeLv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = newText.toLowerCase();

                List<User> filteredList = new ArrayList<>();
                for (User user : userList) {
                    String degree = user.getDegree_level().toLowerCase();
                    if (degree.contains(query)) {
                        filteredList.add(user);
                    }
                }

                ArrayAdapter<User> userAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.adapter_layout_2, R.id.card_fullName, filteredList);
                lv.setAdapter(userAdapter);

                return true;
            }
        });

        s_city.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = newText.toLowerCase();

                List<User> filteredList = new ArrayList<>();
                for (User user : userList) {
                    String city = user.getCity().toLowerCase();
                    if (city.contains(query)) {
                        filteredList.add(user);
                    }
                }

                ArrayAdapter<User> userAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.adapter_layout_2, R.id.card_fullName, filteredList);
                lv.setAdapter(userAdapter);

                return true;
            }
        });



        srtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(UserManager.userArrayList, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {
                        return user1.name.compareToIgnoreCase(user2.name);
                    }
                });
                userAdapter.notifyDataSetChanged();
            }
        });

        //clicking items on the list view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if (!userList.isEmpty()) {

                    Intent intent = new Intent(browsing.this, userInfo.class);

                    intent.putExtra("_id", i + 1);
                    //Toast.makeText(feed.this, "............." + i, Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                } else {
                    // Handle the case when UserManager.userArrayList is empty or not populated
                    Toast.makeText(browsing.this, "User data not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //navigation
        BottomNavigationView BottomNavigation = findViewById(R.id.bottom_navigation);

        BottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_icon1) {
                    Intent intent = new Intent(browsing.this, feed.class);
                    intent.putExtra("currentUserId", getIntent().getIntExtra("currentUserId", -1));
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_icon2) {
                    Intent intent = new Intent(browsing.this, browsing.class);
                    intent.putExtra("currentUserId", getIntent().getIntExtra("currentUserId", -1));
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_icon3) {
                    Intent intent = new Intent(browsing.this, userProfile.class);
                    intent.putExtra("currentUserId", getIntent().getIntExtra("currentUserId", -1));
                    startActivity(intent);
                }
                return false;
            }
        });
    }



}
