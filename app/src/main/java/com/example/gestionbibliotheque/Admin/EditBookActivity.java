package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.R;

import java.util.ArrayList;

public class EditBookActivity extends AppCompatActivity {
    Button bBack, bEdit;
    EditText ETEdit;
    String title;
    DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        bBack = findViewById(R.id.buttonBack8);
        bEdit = findViewById(R.id.buttonEdit);
        ETEdit = findViewById(R.id.editTextEditBook);

        DB = new DataBaseHelper(this);

        bEdit.setOnClickListener(View -> {
            title = ETEdit.getText().toString();
            if (title.isEmpty()) Toast.makeText(EditBookActivity.this, "Veuillez renseignez tous les champs", Toast.LENGTH_SHORT).show();
            else {
                Cursor res = DB.getBookByTitle(title);
                if (res.getCount() != 0) {
                    ArrayList<String> idBook = new ArrayList<>();
                    while (res.moveToNext()) {
                        idBook.add(res.getString(0));
                    }
                    Intent i = new Intent(getApplicationContext(), DetailEditActivity.class);
                    i.putExtra("idBook", idBook.get(0));
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(EditBookActivity.this, "Ce livre n'existe pas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bBack.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ManageBookActivity.class);
            startActivity(i);
            finish();
        });
    }
}