package com.example.gestionbibliotheque;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionbibliotheque.DB.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    //Initializing fields
    DataBaseHelper myDB;
    EditText edit_name, edit_surname, edit_marks, edit_id;
    Button addData, viewData, updateData, deleteData;
    String name, surname, marks, id;
    boolean isUpdated;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //Initialize Database
        myDB = new DataBaseHelper( this );

        //Initialize EditText
        edit_name = findViewById( R.id.name );
        edit_surname = findViewById( R.id.surname );
        edit_marks = findViewById( R.id.marks );
        edit_id = findViewById( R.id.id );

        // TextView
        textView = findViewById( R.id.textView5 );
        textView.setText( "Important Notes:\n1. Both Date and Time will be stored automatically on the time of insertion.\n2. Existing Date and Time will be updated when you update your data." );

        //Initialize Button
        addData = findViewById( R.id.button );
        viewData = findViewById( R.id.button2 );
        updateData = findViewById( R.id.button3 );
        deleteData = findViewById( R.id.button4 );

        //Call Methods
        AddData();
        viewData();
        updateData();
        deleteData();
    }

    //Adding or inserting data to database
    public void AddData(){

        addData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = edit_name.getText().toString();
                surname = edit_surname.getText().toString();
                marks = edit_marks.getText().toString();

                //Current Date and Time
                Date date1 = new Date();
                String date = DateFormat.getDateTimeInstance(). format(date1);

                boolean isInserted = myDB.instertUser( name, surname, marks,true);

                if(isInserted){
                    Toast.makeText( MainActivity.this, "Data is inserted", Toast.LENGTH_SHORT ).show();
                }
                else
                    Toast.makeText( MainActivity.this, "Data is not inserted", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    //For viewing data in database
    public void viewData(){

        viewData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.getAllDataUser();

                if (res.getCount() == 0){
                    showMessage("Error", "Data not found!");
                }

                else{
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        buffer.append( "ID: " + res.getString( 0 ) + "\n" );
                        buffer.append( "Username: " + res.getString( 1 ) + "\n" );
                        buffer.append( "Password: " + res.getString( 2 ) + "\n" );
                        buffer.append( "Email: " + res.getString( 3 ) + "\n" );
                        buffer.append( "Admin: " + res.getString( 4 ) + "\n" );
                    }

                    showMessage( "Data", buffer.toString() );

                }
            }
        } );
    }

    //For updating existing data in database
    public void updateData(){

        updateData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = edit_id.getText().toString();
                name = edit_name.getText().toString();
                surname = edit_surname.getText().toString();
                marks = edit_marks.getText().toString();

                //Current Date and Time
                Date date1 = new Date();
                String date = DateFormat.getDateTimeInstance(). format(date1);

                boolean isUpdated = myDB.updateData( id, name, surname, marks);

                if (isUpdated){
                    showMessage( "Update", "Your data has been successfully updated!" );
                }
                else
                    showMessage( "Update failed", "Cannot Update your data :(" );
            }
        } );
    }

    //For deleting data in the database
    public void deleteData(){

        deleteData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = edit_id.getText().toString();

                Integer res = myDB.deleteData( id );
                if(res > 0){
                    Toast.makeText( getApplicationContext(), "Row effected", Toast.LENGTH_SHORT ).show();
                }
                else{
                    Toast.makeText( getApplicationContext(), "Row not effected", Toast.LENGTH_SHORT ).show();
                }

            }
        } );
    }

    //Method for creating AlertDialog box
    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( message );
        builder.show();
    }
}