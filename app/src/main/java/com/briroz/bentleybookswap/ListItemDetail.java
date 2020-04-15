package com.briroz.bentleybookswap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListItemDetail extends AppCompatActivity implements View.OnClickListener {


    TextView textViewTitle, textViewAuthor, textViewPrice, textViewCategory, textViewIsbn, textViewName, textViewPhone, textViewEmail, textViewLocation;
    ImageView imageView;
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
        textViewIsbn = (TextView) findViewById(R.id.textViewIsbn);
        textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);


        textViewTitle.setText(bookTitle);
        textViewAuthor.setText(bookAuthor);
        textViewPrice.setText(price);
        textViewIsbn.setText(isbn);
        textViewIsbn.setText(isbn);
        textViewCategory.setText(bookCategory);
        textViewName.setText(firstName);
        textViewPhone.setText(phone);
        textViewEmail.setText(email);
        textViewLocation.setText(meetingPlace);


    }

    @Override
    public void onClick(View view) {
        if (isbn != null) {
            // Open Web Activity
            Log.d("TAG", "OPEN WEB CLICKED");
        }
    }
}

