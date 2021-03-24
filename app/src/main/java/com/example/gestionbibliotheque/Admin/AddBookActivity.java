package com.example.gestionbibliotheque.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionbibliotheque.Auth.RegisterActivity;
import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.R;

import java.io.ByteArrayOutputStream;

public class AddBookActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;

    TextView TVTitle, TVUpload;
    EditText ETTitle, ETAuthor, ETCategory, ETPublish_date;
    ImageView IVImage;
    Button bAdd, bBack;
    DataBaseHelper DB;
    String title, author, category, publish;
    byte[] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        DB = new DataBaseHelper(this);

        TVTitle = findViewById(R.id.TVTitleAB);
        TVUpload = findViewById(R.id.TVUploadImage);
        ETTitle = findViewById(R.id.editTextTitle);
        ETAuthor = findViewById(R.id.editTextAuthor);
        ETCategory = findViewById(R.id.editTextCategory);
        ETPublish_date = findViewById(R.id.editTextPublish);
        IVImage = findViewById(R.id.imageViewUpload);
        bAdd = findViewById(R.id.buttonAdd);
        bBack = findViewById(R.id.buttonBack3);

        bBack.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
            startActivity(i);
            finish();
        });

        IVImage.setOnClickListener(view -> {
            if (view.getId() == R.id.imageViewUpload) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });

        bAdd.setOnClickListener(view -> {
            title = ETTitle.getText().toString();
            author = ETAuthor.getText().toString();
            category = ETCategory.getText().toString();
            publish = ETPublish_date.getText().toString();
            if (title.isEmpty() || author.isEmpty() || category.isEmpty() || publish.isEmpty() || IVImage.getDrawable() == null) {
                Toast.makeText( AddBookActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT ).show();
            }
            else {
                boolean isInserted = DB.insertBook(title,author,category,publish,img);
                if (isInserted) {
                    Toast.makeText( AddBookActivity.this, "Votre livre à bien été crée", Toast.LENGTH_SHORT ).show();
                    Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText( AddBookActivity.this, "Erreur !", Toast.LENGTH_SHORT ).show();
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
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}