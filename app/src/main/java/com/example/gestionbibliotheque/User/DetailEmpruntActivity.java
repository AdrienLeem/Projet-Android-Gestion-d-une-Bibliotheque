package com.example.gestionbibliotheque.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.Model.Book;
import com.example.gestionbibliotheque.Model.Emprunt;
import com.example.gestionbibliotheque.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DetailEmpruntActivity extends AppCompatActivity {
    Button bBack, bAjout, bRappel;
    TextView TVEmpruntTitle, TVEmpruntDate;
    EditText ETnbTemps;
    String nbTemps, empruntID;
    DataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_emprunt);

        bBack = findViewById(R.id.buttonBack18);
        bAjout = findViewById(R.id.buttonAjoutTemps);
        bRappel = findViewById(R.id.buttonRappel);
        TVEmpruntTitle = findViewById(R.id.textViewEmprunt1);
        TVEmpruntDate = findViewById(R.id.textViewEmprunt2);
        ETnbTemps = findViewById(R.id.editTextNumberTemps);

        DB = new DataBaseHelper(this);
        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("EmpruntID")) {
                empruntID = intent.getStringExtra("EmpruntID");
            }
            Cursor resEmprunt = DB.getEmpruntByID(empruntID);
            Emprunt emprunt = null;
            if (!(resEmprunt.getCount() == 0)){
                while (resEmprunt.moveToNext()){
                    Cursor resBook = DB.getBookByID(resEmprunt.getString(2));
                    Book book = null;
                    if (!(resBook.getCount() == 0)) {
                        while (resBook.moveToNext()) {
                            book = new Book(resEmprunt.getString(2), resBook.getString(0), resBook.getString(1), resBook.getString(2), resBook.getString(3), resBook.getBlob(4));
                        }
                    }
                    emprunt = new Emprunt(resEmprunt.getString( 0 ), book, resEmprunt.getString( 2 ), resEmprunt.getString( 3 ));
                }
            }
            TVEmpruntTitle.setText("Emprunt de " + emprunt.getBook().getTitle());
            TVEmpruntDate.setText("Emprunté le : " + emprunt.getDate_deb() + ", A rendre le : " + emprunt.getDate_fin());
        }

        bAjout.setOnClickListener(View -> {
            nbTemps = ETnbTemps.getText().toString();
            if (nbTemps.isEmpty()) Toast.makeText(DetailEmpruntActivity.this, "Veuillez renseigner un nombre de jour", Toast.LENGTH_SHORT).show();
            else {
                if (Integer.parseInt(nbTemps) < 0) Toast.makeText(DetailEmpruntActivity.this, "La valeur doit être positive", Toast.LENGTH_SHORT).show();
                else {
                    Cursor res = DB.getEmpruntByID(empruntID);
                    String date_fin = "";
                    if (res.getCount() != 0) {
                        while (res.moveToNext()) {
                            date_fin = res.getString(4);
                        }
                    }
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = null;
                    try {
                        date = formatter.parse(date_fin);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.DATE, Integer.parseInt(nbTemps));
                    date = cal.getTime();
                    date_fin = formatter.format(date);
                    boolean isUpdated = DB.updateEmprunt(empruntID, date_fin);
                    if (isUpdated) {
                        Toast.makeText(DetailEmpruntActivity.this, "Emprunt modifié", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), ConsultEmpruntActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(DetailEmpruntActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bRappel.setOnClickListener(View -> {
            Cursor res = DB.getEmpruntByID(empruntID);
            String date_fin = "";
            Book book = null;
            if (res.getCount() != 0) {
                while (res.moveToNext()) {
                    Cursor resBook = DB.getBookByID(res.getString(2));
                    if (!(resBook.getCount() == 0)) {
                        while (resBook.moveToNext()) {
                            book = new Book(res.getString(2), resBook.getString(0), resBook.getString(1), resBook.getString(2), resBook.getString(3), resBook.getBlob(4));
                        }
                    }
                    date_fin = res.getString(4);
                }
            }
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = formatter.parse(date_fin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            Intent i = new Intent(Intent.ACTION_INSERT);
            i.setType("vnd.android.cursor.item/event");
            i.putExtra(CalendarContract.Events.TITLE, "Rappel rendre" + book.getTitle());
            i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
            i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+60*60*1000);
            i.putExtra(CalendarContract.Events.HAS_ALARM, true);
            i.putExtra(CalendarContract.Reminders.EVENT_ID, CalendarContract.Events._ID);
            i.putExtra(CalendarContract.Events.ALLOWED_REMINDERS, "METHOD_DEFAULT");
            i.putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            i.putExtra(CalendarContract.Reminders.MINUTES,5);
            startActivity(i);
            finish();
        });

        bBack.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ConsultEmpruntActivity.class);
            startActivity(i);
            finish();
        });
    }
}