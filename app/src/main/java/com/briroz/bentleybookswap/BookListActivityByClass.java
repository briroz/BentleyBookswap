package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;


import java.util.ArrayList;

public class BookListActivityByClass extends AppCompatActivity {

    private Spinner classCategory;
    private ListView bookList;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_by_class);
        classCategory = findViewById(R.id.spinnerClassCategory);
        bookList = findViewById(R.id.classSortedBookList);

    }
}
