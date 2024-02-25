package com.example.connect;

import androidx.appcompat.app.AppCompatActivity;
import com.example.connect.dbManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager db_manager = new dbManager(this);
        SQLiteDatabase db = db_manager.getWritableDatabase();
// Perform database operations using the db object


        Button Continue = findViewById(R.id.Continue_button);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FruitManager fruitManager=new FruitManager();
                //fruitManager.removeFruit(position);
                Intent intent=new Intent(MainActivity.this,LogIn.class);
                startActivity(intent);
            }
        });
    }

}
