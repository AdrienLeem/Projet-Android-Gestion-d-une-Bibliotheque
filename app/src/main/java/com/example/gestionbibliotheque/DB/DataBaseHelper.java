package com.example.gestionbibliotheque.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Initialisation de tous les champs nécessaires aux bases de données
    public static final String DATABASE_NAME = "Bibliotheque.db";
    public static final String TABLE_NAME_USER = "user";
    public static final String USER_COL_1 = "ID";
    public static final String USER_COL_2 = "Username";
    public static final String USER_COL_3 = "Password";
    public static final String USER_COL_4 = "Email";
    public static final String USER_COL_5 = "Admin";

    public static final String TABLE_NAME_BOOK = "book";
    public static final String BOOK_COL_1 = "ID";
    public static final String BOOK_COL_2 = "Title";
    public static final String BOOK_COL_3 = "Author";
    public static final String BOOK_COL_4 = "Category";
    public static final String BOOK_COL_5 = "Publish_date";
    public static final String BOOK_COL_6 = "Image_name";

    public static final String TABLE_NAME_BOOK_USER = "book_user";
    public static final String BOOK_USER_COL_1 = "ID";
    public static final String BOOK_USER_COL_2 = "ID_User";
    public static final String BOOK_USER_COL_3 = "ID_Book";
    public static final String BOOK_USER_COL_4 = "Date_deb";
    public static final String BOOK_USER_COL_5 = "Date_fin";

    public static final String LBR = "(";
    public static final String RBR = ")";
    public static final String COM = ",";

    public DataBaseHelper(Context context) {
        super( context, DATABASE_NAME, null, 2 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creation des tables
        db.execSQL( "create table " + TABLE_NAME_USER + LBR + USER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                USER_COL_2 + " TEXT" + COM + USER_COL_3 + " TEXT" + COM + USER_COL_4 + " INTEGER" + COM + USER_COL_5 + " BOOLEAN" + RBR );

        db.execSQL( "create table " + TABLE_NAME_BOOK + LBR + BOOK_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                BOOK_COL_2 + " TEXT" + COM + BOOK_COL_3 + " TEXT" + COM + BOOK_COL_4 + " TEXT" + COM + BOOK_COL_5 + " INTEGER" + COM +
                BOOK_COL_6 + " BLOB" + RBR );

        db.execSQL( "create table " + TABLE_NAME_BOOK_USER + LBR + BOOK_USER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                BOOK_USER_COL_2 + " INTEGER REFERENCES " + TABLE_NAME_USER + LBR + USER_COL_1 + RBR + COM + BOOK_USER_COL_3 +
                " INTEGER REFERENCES " + TABLE_NAME_BOOK + LBR + BOOK_COL_1 + RBR + COM + BOOK_USER_COL_4 + " DATE" + COM + BOOK_USER_COL_5 + " DATE" + RBR );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //Suppression des tables
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME_BOOK);
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME_BOOK_USER);
        onCreate( db );
    }

    //Insert data in database
    public boolean instertUser(String username, String password, String email, Boolean admin){
        SQLiteDatabase db = getWritableDatabase();

        //Passage des valeurs dans la base de données
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_2, username );
        contentValues.put(USER_COL_3, password );
        contentValues.put(USER_COL_4, email );
        contentValues.put(USER_COL_5, admin );

        long result = db.insert(TABLE_NAME_USER, null, contentValues );

        return result != -1;
    }

    public boolean insertBook(String title, String author, String category, String date, byte[] image) {
        SQLiteDatabase db = getWritableDatabase();

        //Passage des valeurs dans la base de données
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_COL_2, title );
        contentValues.put(BOOK_COL_3, author );
        contentValues.put(BOOK_COL_4, category );
        contentValues.put(BOOK_COL_5, date );
        contentValues.put(BOOK_COL_6, image);

        long result = db.insert(TABLE_NAME_BOOK, null, contentValues );

        return result != -1;
    }

    public Cursor getAllDataUser(){
        //Get the data from database
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select * from " + TABLE_NAME_USER, null );
    }

    public Cursor getAllDataBook(){
        //Get the data from database
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select * from " + TABLE_NAME_BOOK, null );
    }

    public Cursor getBookbyID(String idBook) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select Title, Author, Category, Publish_date, Image_name from " + TABLE_NAME_BOOK + " where ID = ?", new String[] { idBook } );
    }

    public Cursor getAllUsername() {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select Username from " + TABLE_NAME_USER, null );
    }

    public Cursor getAllEmail() {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select Email from " + TABLE_NAME_USER, null );
    }

    public Cursor getPasswordByUser(String username) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select Password from " + TABLE_NAME_USER + " where Username = ?", new String[] { username } );
    }

    public Cursor getAdminByUser(String username) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select Admin from " + TABLE_NAME_USER + " where Username = ?", new String[] { username } );
    }

    public boolean isAdmin(Cursor c) {
        if (c.getCount() != 0) {
            ArrayList<String> listAdmin = new ArrayList<>();
            while (c.moveToNext()) {
                listAdmin.add(c.getString(0));
            }
            return listAdmin.contains("1");
        } else return false;
    }

    //Update fields of database using ID (Unique identifier)
    public boolean updateData(String id, String username, String password, String email){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues(  );
        // When you want to update only name field
        if(password.equals( "" ) && email.equals( "" )){
            contentValues.put(USER_COL_1, id );
            contentValues.put(USER_COL_2, username );
        }
        // When you want to update only surname field
        if(username.equals( "" ) && email.equals( "" )){
            contentValues.put(USER_COL_1, id );
            contentValues.put(USER_COL_3, password );
        }
        // When you want to update only marks field
        if(username.equals( "" ) && password.equals( "" )){
            contentValues.put(USER_COL_1, id );
            contentValues.put(USER_COL_4, email );
        }
        // When you want to update name and surname field
        if(email.equals( "" ) && !username.isEmpty() && !password.isEmpty()){
            contentValues.put(USER_COL_1, id );
            contentValues.put(USER_COL_2, username );
            contentValues.put(USER_COL_3, password );
        }
        // When you want to update marks and surname field
        if(username.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            contentValues.put(USER_COL_1, id );
            contentValues.put(USER_COL_3, password );
            contentValues.put(USER_COL_4, email );
        }
        // When you want to update name and marks field
        if(password.isEmpty() && !username.isEmpty() && !email.isEmpty()){
            contentValues.put(USER_COL_1, id );
            contentValues.put(USER_COL_2, username );
            contentValues.put(USER_COL_4, email );
        }
        // When you want to update every data field
        if(!id.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty()){
            contentValues.put(USER_COL_1, id );
            contentValues.put(USER_COL_2, username );
            contentValues.put(USER_COL_3, password );
            contentValues.put(USER_COL_4, email );
        }

        // UPDATE query
        db.update(TABLE_NAME_USER, contentValues, "ID = ?", new String[]{id} );
        return true;
    }

    //Delete data from the databse using ID (Primary Key)
    public Integer deleteData(String id){

        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_USER, "ID = ?", new String [] {id} );
    }
}