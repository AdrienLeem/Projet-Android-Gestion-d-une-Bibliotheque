package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestionbibliotheque.Auth.HomeActivity;
import com.example.gestionbibliotheque.R;
import com.example.gestionbibliotheque.User.ConsultBookActivity;

public class AdminHomeActivity extends AppCompatActivity {
    Button bGererLivre, bGererStock, bConsult, back;
    TextView TVWelcome;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        bGererLivre = findViewById(R.id.buttonGererLivre);
        bGererStock = findViewById(R.id.buttonGererStock);
        bConsult = findViewById(R.id.buttonConsultBook);
        TVWelcome = findViewById(R.id.TVWelcomeMsg);
        back = findViewById(R.id.buttonBack5);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();

        back.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Username", "");
            editor.apply();
            startActivity(i);
            finish();
        });

        if(intent != null) {
            if (intent.hasExtra("Username")) {
                username = intent.getStringExtra("Username");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Username", username);
                editor.apply();
            }
        }

        username = preferences.getString("Username", "");
        TVWelcome.setText("Bonjour, " + username);

        bGererLivre.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ManageBookActivity.class);
            startActivity(i);
            finish();
        });

        bGererStock.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ManageStockActivity.class);
            startActivity(i);
            finish();
        });

        bConsult.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ConsultBookActivity.class);
            i.putExtra("user", username);
            startActivity(i);
            finish();
        });
    }
}