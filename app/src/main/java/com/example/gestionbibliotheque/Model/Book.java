package com.example.gestionbibliotheque.Model;

public class Book {
    private String ID, Title, Author, Category, Publish_date, description;
    private byte[] image;

    public Book(String ID, String title, String author, String category, String publish_date, byte[] image, String description) {
        this.ID = ID;
        this.Title = title;
        this.Author = author;
        this.Category = category;
        this.Publish_date = publish_date;
        this.image = image;
        this.description = description;
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

    public String getDescription() { return description; }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setPublish_date(String publish_date) {
        Publish_date = publish_date;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
