package com.example.gestionbibliotheque.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    public static final String BOOK_COL_7 = "Description";

    public static final String TABLE_NAME_BOOK_USER = "book_user";
    public static final String BOOK_USER_COL_1 = "ID";
    public static final String BOOK_USER_COL_2 = "ID_User";
    public static final String BOOK_USER_COL_3 = "ID_Book";
    public static final String BOOK_USER_COL_4 = "Date_deb";
    public static final String BOOK_USER_COL_5 = "Date_fin";

    public static final String TABLE_NAME_COMMANDE = "commande";
    public static final String COMMANDE_COL_1 = "ID";
    public static final String COMMANDE_COL_2 = "Titre";
    public static final String COMMANDE_COL_3 = "NbExemplaire";
    public static final String COMMANDE_COL_4 = "Date_commande";
    public static final String COMMANDE_COL_5 = "Date_livraison";

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
                BOOK_COL_6 + " BLOB" + COM + BOOK_COL_7 + " TEXT" + RBR );

        db.execSQL( "create table " + TABLE_NAME_BOOK_USER + LBR + BOOK_USER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                BOOK_USER_COL_2 + " INTEGER REFERENCES " + TABLE_NAME_USER + LBR + USER_COL_1 + RBR + COM + BOOK_USER_COL_3 +
                " INTEGER REFERENCES " + TABLE_NAME_BOOK + LBR + BOOK_COL_1 + RBR + COM + BOOK_USER_COL_4 + " TEXT" + COM + BOOK_USER_COL_5 + " TEXT" + RBR );

        db.execSQL( "create table " + TABLE_NAME_COMMANDE + LBR + COMMANDE_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                COMMANDE_COL_2 + " TEXT" + COM + COMMANDE_COL_3 + " TEXT" + COM + COMMANDE_COL_4 + " TEXT" + COM + COMMANDE_COL_5 + " TEXT" + RBR );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //Suppression des tables
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME_BOOK);
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME_BOOK_USER);
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME_COMMANDE);
        onCreate( db );
    }

    //Insert data in database
    public boolean insertUser(String username, String password, String email, Boolean admin){
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

    public boolean insertBook(String title, String author, String category, String date, byte[] image, String description) {
        SQLiteDatabase db = getWritableDatabase();

        //Passage des valeurs dans la base de données
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_COL_2, title );
        contentValues.put(BOOK_COL_3, author );
        contentValues.put(BOOK_COL_4, category );
        contentValues.put(BOOK_COL_5, date );
        contentValues.put(BOOK_COL_6, image);
        contentValues.put(BOOK_COL_7, description);

        long result = db.insert(TABLE_NAME_BOOK, null, contentValues );

        return result != -1;
    }

    public boolean insertCommand(String title, String number) {
        SQLiteDatabase db = getWritableDatabase();

        //Passage des valeurs dans la base de données
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMMANDE_COL_2, title );
        contentValues.put(COMMANDE_COL_3, number );
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateToday = formatter.format(date);
        contentValues.put(COMMANDE_COL_4, dateToday );
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 7);
        date = c.getTime();
        String dateLivraison = formatter.format(date);
        contentValues.put(COMMANDE_COL_5, dateLivraison );

        long result = db.insert(TABLE_NAME_COMMANDE, null, contentValues );

        return result != -1;
    }

    public boolean insertEmprunt(String idUser, String idBook, String date_deb, String date_fin) {
        SQLiteDatabase db = getWritableDatabase();

        //Passage des valeurs dans la base de données
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_USER_COL_2, idUser );
        contentValues.put(BOOK_USER_COL_3, idBook );
        contentValues.put(BOOK_USER_COL_4, date_deb );
        contentValues.put(BOOK_USER_COL_5, date_fin );

        long result = db.insert(TABLE_NAME_BOOK_USER, null, contentValues );

        return result != -1;
    }

    public Cursor getAllDataBook(){
        //Get the data from database
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select * from " + TABLE_NAME_BOOK, null );
    }

    public Cursor getBookByID(String idBook) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select Title, Author, Category, Publish_date, Image_name, Description from " + TABLE_NAME_BOOK + " where ID = ?", new String[] { idBook } );
    }

    public Cursor getBookByTitle(String title) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select ID from " + TABLE_NAME_BOOK + " where Title = ?", new String[] { title } );
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

    public Cursor getIDByUsername(String username) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select ID from " + TABLE_NAME_USER + " where Username = ?", new String[] { username } );
    }

    public Cursor getBookBySearch(String title, String author, String category) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select ID, Title, Author, Category, Publish_date, Image_name from " + TABLE_NAME_BOOK + " where Title LIKE ? AND Author LIKE ? AND Category LIKE ?", new String[] { "%" + title + "%", "%" + author + "%", "%" + category + "%" } );
    }

    public Cursor getAllDataCommand() {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select * from " + TABLE_NAME_COMMANDE, null );
    }

    public Cursor getEmpruntByID(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select * from " + TABLE_NAME_BOOK_USER + " where ID = ?", new String[] { id } );
    }

    public Cursor getEmpruntByIDUser(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery( "select ID, ID_Book, Date_deb, Date_fin from " + TABLE_NAME_BOOK_USER + " where ID_User = ?", new String[] { id } );
    }

    public Integer deleteBook(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_BOOK, "ID = ?", new String [] { id } );
    }

    public boolean updateBook(String id, String title, String author, String category, String publish_date, byte[] image, String description) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_COL_1, id );
        contentValues.put(BOOK_COL_2, title );
        contentValues.put(BOOK_COL_3, author );
        contentValues.put(BOOK_COL_4, category );
        contentValues.put(BOOK_COL_5, publish_date);
        contentValues.put(BOOK_COL_6, image);
        contentValues.put(BOOK_COL_7, description);

        db.update(TABLE_NAME_BOOK, contentValues, "ID = ?", new String[] { id } );
        return true;
    }

    public boolean updateEmprunt(String id, String date_fin) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_USER_COL_5, date_fin );

        db.update(TABLE_NAME_BOOK_USER, contentValues, "ID = ?", new String[] { id } );
        return true;
    }
}