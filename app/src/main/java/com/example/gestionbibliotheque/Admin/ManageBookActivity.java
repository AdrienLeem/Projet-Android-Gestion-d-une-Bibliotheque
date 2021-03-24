package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gestionbibliotheque.R;

public class ManageBookActivity extends AppCompatActivity {
    Button bAddBook, bDeleteBook, bEditBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_book);

        bAddBook = findViewById(R.id.buttonAddBook);
        bDeleteBook = findViewById(R.id.buttonDeleteBook);
        bEditBook = findViewById(R.id.buttonEditBook);

        bAddBook.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), AddBookActivity.class);
            startActivity(i);
            finish();
        });
    }
}