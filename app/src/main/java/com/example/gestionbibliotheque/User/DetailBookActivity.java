package com.example.gestionbibliotheque.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.Model.Book;
import com.example.gestionbibliotheque.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailBookActivity extends AppCompatActivity {
    TextView TVDTtile, TVDAuthor, TVDCategory, TVDPublish;
    EditText dateEmprunt;
    ImageView IVDetail;
    Button bBack, bEmprunt;
    DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        TVDTtile = findViewById(R.id.textViewDetailTitle);
        TVDAuthor = findViewById(R.id.textViewDetailAuthor);
        TVDCategory = findViewById(R.id.textViewDetailCategory);
        TVDPublish = findViewById(R.id.textViewDetailPublish);
        dateEmprunt = findViewById(R.id.editTextDateEmprunt);
        IVDetail = findViewById(R.id.imageViewDetail);
        bBack = findViewById(R.id.bBack1);
        bEmprunt = findViewById(R.id.buttonEmprunt);

        DB = new DataBaseHelper(this);

        Intent intent = getIntent();

        if(intent != null) {
            if (intent.hasExtra("BookID")) {
                String ID = intent.getStringExtra("BookID");
                Cursor resBook = DB.getBookbyID(ID);
                if (resBook.getCount() != 0) {
                    ArrayList<Book> book = new ArrayList<>();
                    while (resBook.moveToNext()) {
                        Book b = new Book(ID, resBook.getString(0), resBook.getString(1), resBook.getString(2), resBook.getString(3), resBook.getBlob(4));
                        book.add(b);
                    }
                    TVDTtile.setText(book.get(0).getTitle());
                    TVDAuthor.setText(book.get(0).getAuthor());
                    TVDCategory.setText(book.get(0).getCategory());
                    TVDPublish.setText(book.get(0).getPublish_date());
                    byte[] bookImage = book.get(0).getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bookImage, 0, bookImage.length);
                    IVDetail.setImageBitmap(bitmap);
                }
            }
        }

        bEmprunt.setOnClickListener(View -> {
            String dateE = dateEmprunt.getText().toString();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
                dateFormat.setLenient(false);
                Date date1 = new Date();
                Date date2 = dateFormat.parse(dateE);
                if (date2.after(date1)) {
                    Toast.makeText(DetailBookActivity.this, "Date valide", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DetailBookActivity.this, "Date invalide", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                Toast.makeText(DetailBookActivity.this, "Format de date invalide", Toast.LENGTH_SHORT).show();
            }

        });

        bBack.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ConsultBookActivity.class);
            startActivity(i);
            finish();
        });
    }
}