package com.example.gestionbibliotheque;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestionbibliotheque.Admin.AdminHomeActivity;
import com.example.gestionbibliotheque.Auth.LoginActivity;
import com.example.gestionbibliotheque.Auth.RegisterActivity;
import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.User.UserHomeActivity;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    DataBaseHelper DB;
    EditText username, password;
    Button bRegister, bLogin;
    String stringUsername, stringPassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        DB = new DataBaseHelper( this );
        
        username = findViewById(R.id.editTextUsernameLogin);
        password = findViewById(R.id.editTextPasswordLogin);
        bLogin = findViewById(R.id.buttonLogin);
        bRegister = findViewById(R.id.buttonRegister);
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = preferences.getString("Username", "");

        if (user.equals("")) {
            bLogin.setOnClickListener(view -> {
                stringUsername = username.getText().toString();
                stringPassword = password.getText().toString();
                //Vérification que tous les champs soient entrées
                if (stringUsername.isEmpty() || stringPassword.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Veuillez renseignez tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor resPassword = DB.getPasswordByUser(stringUsername);
                    //Si data présente, procédure de vérification
                    if (resPassword.getCount() != 0) {
                        ArrayList<String> listPassword = new ArrayList<>();
                        while (resPassword.moveToNext()) {
                            listPassword.add(resPassword.getString(0));
                        }
                        //Si Password match alors connexion
                        if (listPassword.contains(hash(stringPassword))) {
                            Toast.makeText(HomeActivity.this, "Vous etes login", Toast.LENGTH_SHORT).show();
                            if (DB.isAdmin(DB.getAdminByUser(stringUsername))) {
                                Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                                i.putExtra("Username", stringUsername);
                                startActivity(i);
                                finish();
                            } else {
                                Intent i = new Intent(getApplicationContext(), UserHomeActivity.class);
                                i.putExtra("Username", stringUsername);
                                startActivity(i);
                                finish();
                            }
                        }
                        //Si Password ne match alors erreur login
                        else {
                            Toast.makeText(HomeActivity.this, "Mauvais mot de passe", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Si data non présente, Username n'existe pas
                    else {
                        Toast.makeText(HomeActivity.this, "Username n'existe pas", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            bRegister.setOnClickListener(View -> {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            });
        }
        else {
            if (DB.isAdmin(DB.getAdminByUser(user))) {
                Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                i.putExtra("Username",user);
                startActivity(i);
                finish();
            }
            else {
                Intent i = new Intent(getApplicationContext(), UserHomeActivity.class);
                i.putExtra("Username",user);
                startActivity(i);
                finish();
            }
        }
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