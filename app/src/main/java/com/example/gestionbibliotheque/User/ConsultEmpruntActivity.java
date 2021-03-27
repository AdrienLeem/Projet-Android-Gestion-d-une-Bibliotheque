package com.example.gestionbibliotheque.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ListView;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.Model.Book;
import com.example.gestionbibliotheque.Model.Emprunt;
import com.example.gestionbibliotheque.Model.ListViewEmpruntAdapter;
import com.example.gestionbibliotheque.R;

import java.util.ArrayList;

public class ConsultEmpruntActivity extends AppCompatActivity {
    ListView listView;
    Button bBack;
    DataBaseHelper DB;
    ArrayList<Emprunt> listEmprunt = new ArrayList<>();
    String user, IDUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_emprunt);

        DB = new DataBaseHelper(this);

        listView = findViewById(R.id.ListViewEmprunt);
        bBack = findViewById(R.id.buttonBack17);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        user = preferences.getString("Username", "");

        Cursor resID = DB.getIDByUsername(user);

        if (!(resID.getCount() == 0)){
            while (resID.moveToNext()){
                IDUser = resID.getString(0);
            }
        }

        Cursor resEmprunt = DB.getEmpruntByIDUser(IDUser);

        if (!(resEmprunt.getCount() == 0)){
            while (resEmprunt.moveToNext()){
                Cursor resBook = DB.getBookByID(resEmprunt.getString(1));
                Book book = null;
                if (!(resBook.getCount() == 0)) {
                    while (resBook.moveToNext()) {
                        book = new Book(resEmprunt.getString(2), resBook.getString(0), resBook.getString(1), resBook.getString(2), resBook.getString(3), resBook.getBlob(4));
                    }
                }
                Emprunt emprunt = new Emprunt(resEmprunt.getString( 0 ), book, resEmprunt.getString( 2 ), resEmprunt.getString( 3 ));
                listEmprunt.add(emprunt);
            }
        }

        listView.setAdapter(new ListViewEmpruntAdapter(this, this.listEmprunt));

        listView.setOnItemClickListener((a, v, position, id) -> {
            Intent i = new Intent(getApplicationContext(), DetailEmpruntActivity.class);
            Object o = listView.getItemAtPosition(position);
            Emprunt e = (Emprunt) o;
            i.putExtra("EmpruntID", e.getID());
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