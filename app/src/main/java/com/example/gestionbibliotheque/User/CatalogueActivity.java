package com.example.gestionbibliotheque.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gestionbibliotheque.R;

public class CatalogueActivity extends AppCompatActivity {
    Button bBack, bRecherche, bConsult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        bBack = findViewById(R.id.buttonBack15);
        bRecherche = findViewById(R.id.buttonRechercheLivre);
        bConsult = findViewById(R.id.buttonConsultLivre);

        bRecherche.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), SearchBookActivity.class);
            startActivity(i);
            finish();
        });

        bConsult.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ConsultBookActivity.class);
            startActivity(i);
            finish();
        });

        bBack.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), UserHomeActivity.class);
            startActivity(i);
            finish();
        });
    }
}