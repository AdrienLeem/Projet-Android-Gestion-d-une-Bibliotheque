package com.example.gestionbibliotheque.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestionbibliotheque.Auth.HomeActivity;
import com.example.gestionbibliotheque.R;

public class UserHomeActivity extends AppCompatActivity {
    Button bBack, bCatalogue, bConsult;
    TextView TVUserHome;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        bBack = findViewById(R.id.buttonBack14);
        bCatalogue = findViewById(R.id.buttonCatalogue);
        TVUserHome = findViewById(R.id.textViewUserHome);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString("Username", "");
        Intent intent = getIntent();

        if(intent != null) {
            if (intent.hasExtra("Username")) {
                username = intent.getStringExtra("Username");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Username", username);
                editor.apply();
            }
        }

        TVUserHome.setText("Bonjour, " + username);

        bCatalogue.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), CatalogueActivity.class);
            startActivity(i);
            finish();
        });

        bBack.setOnClickListener(View -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Username", "");
            editor.apply();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        });
    }
}