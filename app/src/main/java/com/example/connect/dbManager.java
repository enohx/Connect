package com.example.connect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbManager extends SQLiteOpenHelper {
    //private static final String DATABASE_NAME = "my_database.db";
    //private static final int DATABASE_VERSION = 1;

    public dbManager(Context context) {
        super(context, "UsersDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables and perform initial database setup

            String createTableQuery = "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "Lname TEXT," +
                    "age TEXT," +
                    "degree_level TEXT," +
                    "City TEXT," +
                    "Address TEXT," +
                    "Email TEXT," +
                    "PhoneN TEXT," +
                    "Username TEXT UNIQUE," +
                    "Password TEXT)";

            db.execSQL(createTableQuery);

        String insertQuery = "INSERT INTO users (name, lname, age, degree_level, city, address, email, phoneN, username, password)" +
                             "VALUES ('John', 'Doe', '30', 'Bachelor', 'New York', '123 Main St', 'john.doe@example.com', '1234567890', 'johndoe', 'password123')";
        db.execSQL(insertQuery);



        // This is called when the database is created for the first time
        // Define your table schemas and initial data here
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade database schema and migrate data if needed
        // This is called when the database needs to be upgraded
        // Implement your database schema changes and data migration logic here
    }
}
