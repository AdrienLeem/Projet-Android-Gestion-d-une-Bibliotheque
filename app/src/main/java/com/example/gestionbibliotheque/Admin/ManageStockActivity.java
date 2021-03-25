package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gestionbibliotheque.R;

public class ManageStockActivity extends AppCompatActivity {
    Button bBack, bCommande, bConsult, bEmprunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_stock);

        bBack = findViewById(R.id.buttonBack10);
        bCommande = findViewById(R.id.buttonCommande);
        bConsult = findViewById(R.id.buttonConsultCommande);
        bEmprunt = findViewById(R.id.buttonEmpruntB);

        bBack.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
            startActivity(i);
            finish();
        });

        bCommande.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), CommandBookActivity.class);
            startActivity(i);
            finish();
        });
    }
}