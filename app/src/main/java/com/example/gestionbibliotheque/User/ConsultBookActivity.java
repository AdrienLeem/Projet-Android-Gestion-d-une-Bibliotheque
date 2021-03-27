package com.example.gestionbibliotheque.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gestionbibliotheque.Admin.AdminHomeActivity;
import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.Model.Book;
import com.example.gestionbibliotheque.Model.ListViewBookAdapter;
import com.example.gestionbibliotheque.R;

import java.util.ArrayList;

public class ConsultBookActivity extends AppCompatActivity {
    ListView listView;
    Button bBack;
    DataBaseHelper DB;
    ArrayList<Book> listBook = new ArrayList<>();
    String user, title, author, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_book);

        listView = findViewById(R.id.ListViewBook);
        bBack = findViewById(R.id.button4);
        DB = new DataBaseHelper(this);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("title") && intent.hasExtra("author") && intent.hasExtra("category")) {
                title = intent.getStringExtra("title");
                author = intent.getStringExtra("author");
                category = intent.getStringExtra("category");
                Cursor resBook = DB.getBookBySearch(title, author, category);
                while (resBook.moveToNext()) {
                    Book b = new Book(resBook.getString(0), resBook.getString(1), resBook.getString(2), resBook.getString(3), resBook.getString(4), resBook.getBlob(5));
                    listBook.add(b);
                }
            }
            else {
                Cursor res = DB.getAllDataBook();
                if (!(res.getCount() == 0)){
                    while (res.moveToNext()){
                        Book book = new Book(res.getString( 0 ), res.getString( 1 ), res.getString( 2 ), res.getString( 3 ), res.getString( 4 ), res.getBlob( 5 ));
                        listBook.add(book);
                    }
                }
            }
        }

        listView.setAdapter(new ListViewBookAdapter(this, this.listBook));

        listView.setOnItemClickListener((a, v, position, id) -> {
            Intent i = new Intent(getApplicationContext(), DetailBookActivity.class);
            Object o = listView.getItemAtPosition(position);
            Book b = (Book) o;
            i.putExtra("BookID", b.getID());
            startActivity(i);
            finish();
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        user = preferences.getString("Username", "");

        bBack.setOnClickListener(View -> {
            if (user.equals("")) Toast.makeText( ConsultBookActivity.this, "Erreur !", Toast.LENGTH_SHORT ).show();
            else {
                if (DB.isAdmin(DB.getAdminByUser(user))) {
                    Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(getApplicationContext(), CatalogueActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}