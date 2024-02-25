package com.example.connect;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //declaring variables
        EditText full_name = findViewById(R.id.fullName);
        EditText age = findViewById(R.id.Age);
        EditText profession = findViewById(R.id.Profession);
        EditText degree_level = findViewById(R.id.Degree_level);
        EditText city = findViewById(R.id.City);
        EditText address = findViewById(R.id.Address);
        EditText email = findViewById(R.id.Email);
        EditText phone = findViewById(R.id.Phone);
        EditText username = findViewById(R.id.Username);
        EditText password = findViewById(R.id.sign_up_Password);


        //form User

        Button Register = findViewById(R.id.SignUpButton);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_fullname = full_name.getText().toString();
                int user_age = Integer.parseInt(age.getText().toString());
                String user_profession = profession.getText().toString();
                String user_degreeLV = degree_level.getText().toString();
                String user_City = city.getText().toString();
                String user_address = address.getText().toString();
                String user_email = email.getText().toString();
                String user_phone = phone.getText().toString();
                String user_username = username.getText().toString();
                String user_password = password.getText().toString();
                String user_degree_statement = user_degreeLV + " in " + user_profession;

                User user = new User(user_fullname,
                        user_age,
                        user_degree_statement,
                        user_City,
                        user_address,
                        user_email,
                        user_phone,
                        user_username,
                        user_password);
                UserManager.userArrayList.add(user);

                // Save the user information to the database
                dbManager db_manager = new dbManager(SignUp.this);
                SQLiteDatabase db = db_manager.getWritableDatabase();

                String[] projection = { "Username" };
                String selection = "Username = ?";
                String[] selectionArgs = { user_username };

                Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
                boolean usernameExists = cursor.moveToFirst();

                cursor.close();
                db.close();


                if (usernameExists) {
                    // Display a pop-up message or toast indicating the username already exists
                    Toast.makeText(SignUp.this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the user information to the database
                    db = db_manager.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("name", user.getName());
                    values.put("Lname", user.getLname());
                    values.put("age", user.getAge());
                    values.put("degree_level", user.getDegree_level());
                    values.put("City", user.getCity());
                    values.put("Address", user.getAddress());
                    values.put("Email", user.getEmail());
                    values.put("PhoneN", user.getPhoneN());
                    values.put("Username", user.getUsername());
                    values.put("Password", user.getPassword());

                    long newRowId = db.insert("users", null, values);
                    if (newRowId != -1) {
                        Log.d(TAG, "New record inserted with ID: " + newRowId);
                    } else {
                        Log.e(TAG, "Error inserting new record");
                    }

                    db.close();

                    Intent intent = new Intent(SignUp.this, LogIn.class);
                    startActivity(intent);
                }
            }
        });

    }
}