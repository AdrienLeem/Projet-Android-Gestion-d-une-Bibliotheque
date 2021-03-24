package com.example.gestionbibliotheque.Auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.R;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    DataBaseHelper DB;
    EditText username, password, email;
    Button back,validate;
    CheckBox checkBox;
    String stringUsername, stringPassword, stringEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DB = new DataBaseHelper( this );

        username = findViewById(R.id.editTextUsernameRegister);
        password = findViewById(R.id.editTextPasswordRegister);
        email = findViewById(R.id.editTextEmailAddressRegister);
        checkBox = findViewById(R.id.checkBoxRegister);
        validate = findViewById(R.id.buttonRegisterValidate);
        back = findViewById(R.id.buttonBack1);

        back.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        });

        validate.setOnClickListener(view -> {
            stringUsername = username.getText().toString();
            stringPassword = password.getText().toString();
            stringEmail = email.getText().toString();
            //Vérification que tous les champs soient entrées
            if (stringUsername.isEmpty() || stringPassword.isEmpty() || stringEmail.isEmpty()) {
                Toast.makeText( RegisterActivity.this, "Veuillez renseignez tous les champs", Toast.LENGTH_SHORT ).show();
            }
            //Vérification que le username et l'Email soit unique
            else {
                Cursor resUsername = DB.getAllUsername();
                Cursor resEmail = DB.getAllEmail();
                //Si data présente, procédure de vérification
                if (resUsername.getCount() != 0){
                    ArrayList<String> listUsername = new ArrayList<>();
                    ArrayList<String> listEmail = new ArrayList<>();
                    while (resUsername.moveToNext()) {
                        listUsername.add(resUsername.getString(0));
                    }
                    while (resEmail.moveToNext()) {
                        listEmail.add(resEmail.getString(0));
                    }
                    //Vérification username
                    if (listUsername.contains(stringUsername)) Toast.makeText( RegisterActivity.this, "Votre username n'est pas unique", Toast.LENGTH_SHORT ).show();
                    else {
                        //Vérification email
                        if (listEmail.contains(stringEmail)) {
                            Toast.makeText( RegisterActivity.this, "Votre Email n'est pas unique", Toast.LENGTH_SHORT ).show();
                        } else {
                            //Si data non présente création du compte
                            boolean isInserted = DB.instertUser(stringUsername,hash(stringPassword),stringEmail,checkBox.isChecked());
                            if (isInserted) {
                                Toast.makeText(RegisterActivity.this, "Votre compte a bien été crée !", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else Toast.makeText( RegisterActivity.this, "Erreur !", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } else {
                    //Si data non présente, création du compte
                    boolean isInserted = DB.instertUser(stringUsername,hash(stringPassword),stringEmail,checkBox.isChecked());
                    if (isInserted) {
                        Toast.makeText(RegisterActivity.this, "Votre compte a bien été crée !", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else Toast.makeText( RegisterActivity.this, "Erreur !", Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

    private String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1"); //could also be MD5, SHA-256 etc.
            md.reset();
            md.update(password.getBytes("UTF-8"));
            byte[] resultByte = md.digest();
            password = String.format("%01x", new java.math.BigInteger(1, resultByte));
        } catch (NoSuchAlgorithmException e) {
            //do something.
        } catch (UnsupportedEncodingException ex) {
            //do something
        }
        return password;
    }
}