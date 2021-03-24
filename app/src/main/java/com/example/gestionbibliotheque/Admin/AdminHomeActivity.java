package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestionbibliotheque.R;

public class AdminHomeActivity extends AppCompatActivity {
    Button bAdd, bDelete, bEdit;
    TextView TVWelcome;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        bAdd = findViewById(R.id.buttonAddBook);
        bDelete = findViewById(R.id.buttonDeleteBook);
        bEdit = findViewById(R.id.buttonEditBook);
        TVWelcome = findViewById(R.id.TVWelcomeMsg);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();

        if(intent != null) {
            if (intent.hasExtra("Username")) {
                username = intent.getStringExtra("Username");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Username", username);
                editor.apply();
                TVWelcome.setText(username);
            }
        }

        /*back.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        });*/
    }
}