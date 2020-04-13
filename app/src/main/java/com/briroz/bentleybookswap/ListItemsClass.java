package com.briroz.bentleybookswap;

public class ListItemsClass {

    public int key; // Primary key for object
    public String author; // Book author
    public String title; // Book Title

    public ListItemsClass(String title, String author) {
        this.author = author;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getKey() {
        return key;
    }

}
