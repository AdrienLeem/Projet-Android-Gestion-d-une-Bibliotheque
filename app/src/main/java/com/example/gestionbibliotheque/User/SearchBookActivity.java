package com.example.gestionbibliotheque.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.Model.Book;
import com.example.gestionbibliotheque.R;

import java.util.ArrayList;

public class SearchBookActivity extends AppCompatActivity {
    Button bBack, bSearch;
    EditText ETTitle, ETAuthor, ETCategory;
    String title, author, category;
    DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        bBack = findViewById(R.id.buttonBack16);
        bSearch = findViewById(R.id.buttonSearch);
        ETTitle = findViewById(R.id.editTextTextTitle);
        ETAuthor = findViewById(R.id.editTextTextAuthor);
        ETCategory = findViewById(R.id.editTextTextCategory);

        DB = new DataBaseHelper(this);

        bSearch.setOnClickListener(View -> {
            title = ETTitle.getText().toString();
            author = ETAuthor.getText().toString();
            category = ETCategory.getText().toString();
            if (title.isEmpty() && author.isEmpty() && category.isEmpty()) Toast.makeText(SearchBookActivity.this, "Veuillez renseigner au moins 1 champ", Toast.LENGTH_SHORT).show();
            else {
                Cursor resBook = DB.getBookBySearch(title, author, category);
                if (resBook.getCount() == 0) Toast.makeText(SearchBookActivity.this, "Aucun livre correspondant", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(getApplicationContext(), ConsultBookActivity.class);
                    i.putExtra("title", title);
                    i.putExtra("author", author);
                    i.putExtra("category", category);
                    startActivity(i);
                    finish();
                }
            }
        });

        bBack.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), CatalogueActivity.class);
            startActivity(i);
            finish();
        });
    }
}