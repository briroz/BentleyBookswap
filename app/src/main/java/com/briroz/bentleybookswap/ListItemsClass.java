package com.briroz.bentleybookswap;

public class ListItemsClass {

    public int key; // Primary key for object
    public String author; // Book author
    public String title; // Book Title
    public String isbn; // ISBN to be used for a Url for image to be displayed
    public String category; // Class code to be displayed
    public String price;  // String to avoid special character conflict ($)

    public ListItemsClass(int k, String t, String a, String c, String i) {
        this.author = a;
        this.title = t;
        this.key = k;
        this.isbn = i;
        this.category = c;
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

    public String getIsbn() {
    return isbn;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }
}
