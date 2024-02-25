package com.example.connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class edit_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //declaring variables
        TextView editName = findViewById(R.id.edit_name);
        TextView editAge = findViewById(R.id.edit_age);
        TextView editProfession = findViewById(R.id.edit_degree);
        TextView editDegreeLevel = findViewById(R.id.edit_degree_level);
        TextView editLocation = findViewById(R.id.edit_location_street);
        TextView editAddress = findViewById(R.id.edit_location);
        TextView editEmail = findViewById(R.id.edit_email);
        TextView editPhoneN = findViewById(R.id.edit_phone);
        int receivedUserId = getIntent().getIntExtra("UserId", -1);

        //connecting to database
        dbManager dbManager = new dbManager(edit_profile.this);
        SQLiteDatabase db = dbManager.getReadableDatabase();

        // Retrieve the original values from the database based on the received user ID
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE id = " + receivedUserId, null);
        String originalUserName = "";
        String originalUserLname = "";
        String originalUserAge = "";
        String originalUserDegreeLevel = "";
        String originalUserCity = "";
        String originalUserAddress = "";
        String originalUserEmail = "";
        String originalUserPhone = "";

        if (cursor.moveToFirst()) {
            originalUserName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            originalUserLname = cursor.getString(cursor.getColumnIndexOrThrow("Lname"));
            originalUserAge = cursor.getString(cursor.getColumnIndexOrThrow("age"));
            originalUserDegreeLevel = cursor.getString(cursor.getColumnIndexOrThrow("degree_level"));
            originalUserCity = cursor.getString(cursor.getColumnIndexOrThrow("City"));
            originalUserAddress = cursor.getString(cursor.getColumnIndexOrThrow("Address"));
            originalUserEmail = cursor.getString(cursor.getColumnIndexOrThrow("Email"));
            originalUserPhone = cursor.getString(cursor.getColumnIndexOrThrow("PhoneN"));
            String originalNameStatment = originalUserName + " " + originalUserLname;
            //String[] splitStr = originalUserDegreeLevel.trim().split("\\s+");
            //String originalDegreeLevel = splitStr[0];
           // String originalDegree = splitStr[1];

            //setting the values of the fields according to the current info abt the user
            editName.setText(originalNameStatment);
            editAge.setText(originalUserAge);
            editProfession.setText(originalUserDegreeLevel);
            editDegreeLevel.setText(originalUserDegreeLevel);
            editLocation.setText(originalUserCity);
            editAddress.setText(originalUserAddress);
            editEmail.setText(originalUserEmail);
            editPhoneN.setText(originalUserPhone);

        }
        //close database connection
        cursor.close();
        db.close();


        Button update_button =  findViewById(R.id.update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(edit_profile.this, "aaa " + receivedUserId, Toast.LENGTH_SHORT).show();
                String UserFullName = editName.getText().toString();
                String[] splitStr = UserFullName.trim().split("\\s+");
                String userName = splitStr[0];
                String userLname = splitStr[1];
                String userAge = editAge.getText().toString();
                String userDegree = editProfession.getText().toString();
                String userDegreeLevel = editDegreeLevel.getText().toString();
                String userCity = editLocation.getText().toString();
                String userAddress = editAddress.getText().toString();
                String userEmail = editEmail.getText().toString();
                String userPhone = editPhoneN.getText().toString();

                String degreeStatement = userDegreeLevel + " in " + userDegree;


                dbManager dbManager = new dbManager(edit_profile.this);
                SQLiteDatabase db = dbManager.getWritableDatabase();

                // Create the update query
                String updateQuery = "UPDATE users SET " +
                        "name = '" + userName + "', " +
                        "Lname = '" + userLname + "', " +
                        "age = '" + userAge + "', " +
                        "degree_level = '" + degreeStatement + "', " +
                        "City = '" + userCity + "', " +
                        "Address = '" + userAddress + "', " +
                        "Email = '" + userEmail + "', " +
                        "PhoneN = '" + userPhone + "' " +
                        "WHERE id = " + receivedUserId;

                // Execute the update query
                db.execSQL(updateQuery);

                // Close the database connection
                db.close();

                Intent intent=new Intent(edit_profile.this,userProfile.class);
                intent.putExtra("currentUserId", receivedUserId);
                startActivity(intent);
            }
        });

        Button deleteBtn = findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a writable database instance
                dbManager dbManager = new dbManager(edit_profile.this);
                SQLiteDatabase db = dbManager.getWritableDatabase();

                // Define the WHERE clause to specify the record to delete
                String whereClause = "id = ?";
                String[] whereArgs = {String.valueOf(receivedUserId)};

                // Perform the delete operation
                int rowsAffected = db.delete("users", whereClause, whereArgs);

                if (rowsAffected > 0) {
                    // Deletion successful
                    Toast.makeText(edit_profile.this, "Record deleted", Toast.LENGTH_SHORT).show();
                } else {
                    // Deletion unsuccessful or no matching record found
                    Toast.makeText(edit_profile.this, "Failed to delete record", Toast.LENGTH_SHORT).show();
                }

                db.close();

                Intent intentNew=new Intent(edit_profile.this,LogIn.class);
                startActivity(intentNew);

            }
        });


    }

}