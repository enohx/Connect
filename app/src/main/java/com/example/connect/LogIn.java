package com.example.connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

     int currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        EditText Username = findViewById(R.id.username);
        EditText Password = findViewById(R.id.password);

        TextView sign_up_link = findViewById(R.id.signUp_text);
        SpannableString span_String = new SpannableString("Click here to Register Now!");
        ClickableSpan clickable_Span = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogIn.this,SignUp.class);
                startActivity(intent);
            }
        };
        span_String.setSpan(clickable_Span, 6, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sign_up_link.setText(span_String);
        sign_up_link.setMovementMethod(LinkMovementMethod.getInstance());


        Button LoginButton = findViewById(R.id.LoginBUtton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString();
                String password = Password.getText().toString();

                /*String[] stringarray = {username, password};
                Intent username_intent = new Intent(LogIn.this, userProfile.class);
                username_intent.putExtra("stringarray", stringarray);*/


                // Check if a user with the given username exists
                dbManager db_manager = new dbManager(LogIn.this);
                SQLiteDatabase db = db_manager.getReadableDatabase();

                String[] projection = { "id","Username" };
                String selection = "Username = ? AND Password = ?";
                String[] selectionArgs = { username, password };

                Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
                boolean userExists = cursor.moveToFirst();



                if (userExists) {
                    int userIdColumnIndex = cursor.getColumnIndexOrThrow("id");
                    currentUserId = cursor.getInt(userIdColumnIndex);


                    cursor.close();
                    db.close();

                    // User exists, proceed to the feed page
                    Intent intent = new Intent(LogIn.this, feed.class);
                    intent.putExtra("currentUserId", currentUserId);
                    startActivity(intent);
                } else {
                    // User does not exist, display a message
                    Toast.makeText(LogIn.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                    cursor.close();
                    db.close();
                }
            }
        });
    }
}