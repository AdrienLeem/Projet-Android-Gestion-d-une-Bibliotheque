package com.example.gestionbibliotheque;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import com.example.gestionbibliotheque.Admin.AdminHomeActivity;
import com.example.gestionbibliotheque.Auth.LoginActivity;
import com.example.gestionbibliotheque.Auth.RegisterActivity;
import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.User.UserHomeActivity;

public class HomeActivity extends AppCompatActivity {
    DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("Username", "");

        DB = new DataBaseHelper( this );

        Button bRegister = findViewById(R.id.buttonRegister);
        Button bLogin = findViewById(R.id.buttonLogin);

        if (username.equals("")) {
            bRegister.setOnClickListener(view -> {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            });

            bLogin.setOnClickListener(view -> {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            });
        } else {
            if (DB.isAdmin(DB.getAdminByUser(username))) {
                Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                i.putExtra("Username",username);
                startActivity(i);
                finish();
            }
            else {
                Intent i = new Intent(getApplicationContext(), UserHomeActivity.class);
                i.putExtra("Username",username);
                startActivity(i);
                finish();
            }
        }
    }
}