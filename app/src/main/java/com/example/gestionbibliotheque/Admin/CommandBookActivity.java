package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestionbibliotheque.Auth.RegisterActivity;
import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.R;

public class CommandBookActivity extends AppCompatActivity {
    Button bBack, bCommande;
    EditText ETTtitle, ETNombre;
    String title, number;
    DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_book);

        bBack = findViewById(R.id.buttonBack11);
        bCommande = findViewById(R.id.buttonCommandBook1);
        ETTtitle = findViewById(R.id.editTextTitleC);
        ETNombre = findViewById(R.id.editTextNumberC);

        DB = new DataBaseHelper(this);

        bBack.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ManageStockActivity.class);
            startActivity(i);
            finish();
        });

        bCommande.setOnClickListener(View -> {
            title = ETTtitle.getText().toString();
            number = ETNombre.getText().toString();
            if (title.isEmpty() || number.isEmpty()) Toast.makeText(CommandBookActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
            else {
                boolean isInserted = DB.insertCommand(title, number);
                if (isInserted) {
                    Toast.makeText(CommandBookActivity.this, "Votre Commande a bien été crée !", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText( CommandBookActivity.this, "Erreur !", Toast.LENGTH_SHORT ).show();
            }
        });
    }
}