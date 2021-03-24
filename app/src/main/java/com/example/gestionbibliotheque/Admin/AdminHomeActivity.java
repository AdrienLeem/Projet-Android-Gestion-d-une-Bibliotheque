package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.R;
import com.example.gestionbibliotheque.User.ConsultBookActivity;

public class AdminHomeActivity extends AppCompatActivity {
    Button bGererLivre, bGererStock, bConsult;
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

        bGererLivre.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ManageBookActivity.class);
            startActivity(i);
            finish();
        });

        bConsult.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ConsultBookActivity.class);
            startActivity(i);
            finish();
            /*Cursor res = DB.getAllDataBook();

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
                    buffer.append( "Nom image: " + res.getBlob( 5 ) + "\n" );
                }
                showMessage( "Data", buffer.toString() );
            }*/
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