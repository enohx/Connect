package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.shake.shakeDetectorManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class userProfile extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private shakeDetectorManager shakeDetector_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView name_line = findViewById(R.id.profession_card_name);
        TextView degree_line = findViewById(R.id.profession_card_degree);
        TextView address_line = findViewById(R.id.profession_card_address);
        TextView email_line = findViewById(R.id.profession_card_email);
        TextView phone_line = findViewById(R.id.profession_card_phone);
        TextView compensation_line = findViewById(R.id.profession_card_PayRate);

        int receivedUserId = getIntent().getIntExtra("currentUserId", -1);
        //pass the user Id the the edit profile page


        // getting the record from the database with the matching id
        dbManager db_manager = new dbManager(userProfile.this);
        SQLiteDatabase db = db_manager.getReadableDatabase();
        String[] projection = {"name", "Lname", "age", "degree_level", "City", "Address", "Email", "PhoneN"};
        String selection = "id = ?";
        String[] selectionArgs={String.valueOf(receivedUserId)};
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        boolean proof = cursor.moveToFirst();



        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String Lname = cursor.getString(cursor.getColumnIndexOrThrow("Lname"));
        int age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
        String degree_level = cursor.getString(cursor.getColumnIndexOrThrow("degree_level"));
        String city = cursor.getString(cursor.getColumnIndexOrThrow("City"));
        String address = cursor.getString(cursor.getColumnIndexOrThrow("Address"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("Email"));
        String phoneN = cursor.getString(cursor.getColumnIndexOrThrow("PhoneN"));

        String name_statement = name + " " + Lname + ", " + age;
        String location_statement = address + ", " + city;
        String phone_statement = "Phone number: " + phoneN;
        String email_statement = "Email: " + email;

        //Putting the user information in the profile card
        name_line.setText(name_statement);
        degree_line.setText(degree_level);
        address_line.setText(location_statement);
        email_line.setText(email_statement);
        phone_line.setText(phone_statement);
        compensation_line.setText("Pay Rate: 150$");




        //navigation menu
        BottomNavigationView BottomNavigation = findViewById(R.id.bottom_navigation);
        BottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_icon1) {
                    Intent intent=new Intent(userProfile.this, feed.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_icon2) {
                    Intent intent=new Intent(userProfile.this, browsing.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_icon3) {
                    Intent intent=new Intent(userProfile.this, userProfile.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        //update process
        Button edit_button =  findViewById(R.id.editButton);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(userProfile.this, "aaa " + receivedUserId, Toast.LENGTH_SHORT).show();
                Intent Edit_intent = new Intent(userProfile.this, edit_profile.class);
                Edit_intent.putExtra("UserId", receivedUserId);
                startActivity(Edit_intent);
                //Intent intent=new Intent(userProfile.this,edit_profile.class);
                //startActivity(intent);
            }
        });

        //sign out process
        TextView sign_out_link = findViewById(R.id.sign_out_text);
        SpannableString span_String = new SpannableString("Sign out here !");
        ClickableSpan clickable_Span = new ClickableSpan() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Clickable Text Clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(userProfile.this,MainActivity.class);
                startActivity(intent);
            }
        };
        span_String.setSpan(clickable_Span, 8, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sign_out_link.setText(span_String);
        sign_out_link.setMovementMethod(LinkMovementMethod.getInstance());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize the shake detector
        shakeDetector_manager = new shakeDetectorManager();
        shakeDetector_manager.setOnShakeListener(new shakeDetector_manager.OnShakeListener() {
            @Override
            public void onShake(int count) {
                Intent intent = new Intent(userProfile.this, feed.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector_manager, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector_manager);
        super.onPause();
    }
}