package com.example.gestionbibliotheque.Model;

public class Book {
    private String ID, Title, Author, Category, Publish_date;
    private byte [] image;

    public Book(String ID, String title, String author, String category, String publish_date, byte[] image) {
        this.ID = ID;
        Title = title;
        Author = author;
        Category = category;
        Publish_date = publish_date;
        this.image = image;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getCategory() {
        return Category;
    }

    public String getPublish_date() {
        return Publish_date;
    }

    public byte[] getImage() {
        return image;
    }
}
