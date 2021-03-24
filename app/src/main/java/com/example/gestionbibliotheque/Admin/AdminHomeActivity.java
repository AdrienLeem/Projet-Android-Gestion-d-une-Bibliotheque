package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.R;

public class AdminHomeActivity extends AppCompatActivity {
    Button bAdd, bDelete, bEdit, bConsult;
    TextView TVWelcome;
    String username;
    DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        DB = new DataBaseHelper(this);

        bAdd = findViewById(R.id.buttonAddBook);
        bDelete = findViewById(R.id.buttonDeleteBook);
        bEdit = findViewById(R.id.buttonEditBook);
        bConsult = findViewById(R.id.buttonConsultBook);
        TVWelcome = findViewById(R.id.TVWelcomeMsg);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();

        if(intent != null) {
            if (intent.hasExtra("Username")) {
                username = intent.getStringExtra("Username");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Username", username);
                editor.apply();
                TVWelcome.setText("Bonjour " + username);
            }
        }

        bConsult.setOnClickListener(View -> {
            Cursor res = DB.getAllDataBook();

            if (res.getCount() == 0){
                showMessage("Error", "Data not found!");
            }
            else{
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append( "ID: " + res.getString( 0 ) + "\n" );
                    buffer.append( "Title: " + res.getString( 1 ) + "\n" );
                    buffer.append( "Author: " + res.getString( 2 ) + "\n" );
                    buffer.append( "Categorie: " + res.getString( 3 ) + "\n" );
                    buffer.append( "Date publication: " + res.getString( 4 ) + "\n" );
                    buffer.append( "Nom image: " + res.getString( 5 ) + "\n" );
                }
                showMessage( "Data", buffer.toString() );
            }
        });

        bAdd.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddBookActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( message );
        builder.show();
    }
}