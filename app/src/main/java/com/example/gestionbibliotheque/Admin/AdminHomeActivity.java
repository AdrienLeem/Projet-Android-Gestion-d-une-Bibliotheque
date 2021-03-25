package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestionbibliotheque.Auth.HomeActivity;
import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.R;
import com.example.gestionbibliotheque.User.ConsultBookActivity;

public class AdminHomeActivity extends AppCompatActivity {
    Button bGererLivre, bGererStock, bConsult, back;
    TextView TVWelcome;
    String username;
    DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        DB = new DataBaseHelper(this);

        bGererLivre = findViewById(R.id.buttonGererLivre);
        bGererStock = findViewById(R.id.buttonGererStock);
        bConsult = findViewById(R.id.buttonConsultBook);
        TVWelcome = findViewById(R.id.TVWelcomeMsg);
        back = findViewById(R.id.buttonBack5);

        back.setOnClickListener(view -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Username", "");
            editor.apply();
            startActivity(i);
            finish();
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();

        if(intent != null) {
            if (intent.hasExtra("Username")) {
                username = intent.getStringExtra("Username");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Username", username);
                editor.apply();
            }
        }

        TVWelcome.setText("Bonjour, " + preferences.getString("Username", ""));

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
            startActivity(i);
            finish();
        });
    }
}