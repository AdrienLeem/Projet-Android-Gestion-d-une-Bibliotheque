package com.example.gestionbibliotheque.Model;

public class Emprunt {
    private String ID, date_deb, date_fin;
    private Book book;

    public Emprunt(String ID, Book book, String date_deb, String date_fin) {
        this.ID = ID;
        this.book = book;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
    }

    public String getID() {
        return ID;
    }

    public String getDate_deb() {
        return date_deb;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public Book getBook() {
        return book;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDate_deb(String date_deb) {
        this.date_deb = date_deb;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
