package com.briroz.bentleybookswap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ListItemDetail extends AppCompatActivity {


    TextView textViewTitle, textViewAuthor, textViewPrice, textViewCategory, textViewName, textViewPhone, textViewEmail, textViewLocation;
    Button buttonOpenMap, buttonOpenDialer, buttonOpenEmail;
    String itemKey, firstName, bookTitle, bookAuthor, isbn, price, meetingPlace, phone, email, bookCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_item_detail);

        Intent intent = getIntent();
        itemKey = intent.getStringExtra("itemKey");
        firstName = intent.getStringExtra("firstName");
        bookTitle = intent.getStringExtra("bookTitle");
        bookAuthor = intent.getStringExtra("bookAuthor");
        isbn = intent.getStringExtra("isbn");
        price = intent.getStringExtra("price");
        meetingPlace = intent.getStringExtra("meetingPlace");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        bookCategory = intent.getStringExtra("bookCategory");


        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);


        textViewTitle.setText(bookTitle);
        textViewAuthor.setText(bookAuthor);
        textViewPrice.setText(price);
        textViewCategory.setText(bookCategory);
        textViewName.setText(firstName);
        textViewPhone.setText(phone);
        textViewEmail.setText(email);
        textViewLocation.setText(meetingPlace);




    }
}
