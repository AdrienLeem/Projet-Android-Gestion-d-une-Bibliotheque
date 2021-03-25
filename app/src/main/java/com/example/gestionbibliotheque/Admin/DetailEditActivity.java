package com.example.gestionbibliotheque.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.Model.Book;
import com.example.gestionbibliotheque.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DetailEditActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;

    TextView TVTitle, TVUpload;
    EditText ETTitle, ETAuthor, ETCategory, ETPublish_date;
    ImageView IVImage;
    Button bEdit, bBack;
    DataBaseHelper DB;
    String id, title, author, category, publish;
    byte[] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edit);

        DB = new DataBaseHelper(this);

        TVTitle = findViewById(R.id.TVTitleDB);
        TVUpload = findViewById(R.id.TVUploadImage2);
        ETTitle = findViewById(R.id.editTextTitle2);
        ETAuthor = findViewById(R.id.editTextAuthor2);
        ETCategory = findViewById(R.id.editTextCategory2);
        ETPublish_date = findViewById(R.id.editTextPublish2);
        IVImage = findViewById(R.id.imageViewUpload2);
        bEdit = findViewById(R.id.buttonEdit2);
        bBack = findViewById(R.id.buttonBack9);

        Intent i = getIntent();

        if (i != null) {
            if (i.hasExtra("idBook")) {
                id = i.getStringExtra("idBook");
                Cursor res = DB.getBookByID(id);
                if (res.getCount() != 0) {
                    ArrayList<Book> book = new ArrayList<>();
                    while (res.moveToNext()) {
                        Book b = new Book(id, res.getString(0), res.getString(1), res.getString(2), res.getString(3), res.getBlob(4));
                        book.add(b);
                    }
                    ETTitle.setText(book.get(0).getTitle());
                    ETAuthor.setText(book.get(0).getAuthor());
                    ETCategory.setText(book.get(0).getCategory());
                    ETPublish_date.setText(book.get(0).getPublish_date());
                    byte[] bookImage = book.get(0).getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bookImage, 0, bookImage.length);
                    IVImage.setImageBitmap(bitmap);
                }
            }
        }

        bBack.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ManageBookActivity.class);
            startActivity(intent);
            finish();
        });

        IVImage.setOnClickListener(view -> {
            if (view.getId() == R.id.imageViewUpload) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });

        bEdit.setOnClickListener(view -> {
            title = ETTitle.getText().toString();
            author = ETAuthor.getText().toString();
            category = ETCategory.getText().toString();
            publish = ETPublish_date.getText().toString();
            img = imageViewToByte(IVImage);
            if (title.isEmpty() || author.isEmpty() || category.isEmpty() || publish.isEmpty() || IVImage.getDrawable() == null) {
                Toast.makeText(DetailEditActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                boolean isUpdated = DB.updateBook(id, title, author, category, publish, img);
                if (isUpdated) {
                    Toast.makeText(DetailEditActivity.this, "Votre livre à bien été modifié", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), EditBookActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DetailEditActivity.this, "Erreur !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            IVImage.setImageURI(selectedImage);
            img = imageViewToByte(IVImage);
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}