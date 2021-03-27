package com.example.gestionbibliotheque.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.Model.Commande;
import com.example.gestionbibliotheque.Model.ListViewCommandAdapter;
import com.example.gestionbibliotheque.R;

import java.util.ArrayList;

public class ConsultCommandActivity extends AppCompatActivity {
    ListView listView;
    Button bBack;
    DataBaseHelper DB;
    ArrayList<Commande> listCommand = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_command);

        DB = new DataBaseHelper(this);

        listView = findViewById(R.id.ListViewCommand);
        bBack = findViewById(R.id.buttonBack13);

        Cursor res = DB.getAllDataCommand();

        if (!(res.getCount() == 0)){
            while (res.moveToNext()){
                Commande commande = new Commande(res.getString( 0 ), res.getString( 1 ), res.getString( 2 ), res.getString( 3 ), res.getString( 4 ));
                listCommand.add(commande);
            }
        }

        listView.setAdapter(new ListViewCommandAdapter(this, this.listCommand));

        bBack.setOnClickListener(View -> {
            Intent i = new Intent(getApplicationContext(), ManageStockActivity.class);
            startActivity(i);
            finish();
        });
    }
}