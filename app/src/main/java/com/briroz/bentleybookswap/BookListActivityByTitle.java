package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

public class BookListActivityByTitle extends AppCompatActivity {

    private EditText bookTitleSearch;
    private ListView bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_by_title);
        bookList = findViewById(R.id.titleSortedBookList);
        bookTitleSearch = findViewById(R.id.editTextTitleSearch);

    }
}
