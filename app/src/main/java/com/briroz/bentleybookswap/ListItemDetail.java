package com.briroz.bentleybookswap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemDetail extends AppCompatActivity implements View.OnClickListener {


    TextView textViewTitle, textViewAuthor, textViewPrice, textViewCategory, textViewIsbn, textViewName, textViewPhone, textViewEmail, textViewLocation;
    ImageView imageView;
    Button buttonMap, buttonDialer, buttonSMS, buttonEmail;
    ImageButton buttonBack;
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
        buttonSMS = (Button) findViewById(R.id.buttonText);
        buttonMap = (Button) findViewById(R.id.buttonMap);
        buttonEmail = (Button) findViewById(R.id.buttonEmail);
        buttonDialer = (Button) findViewById(R.id.buttonCall);
        buttonBack = (ImageButton) findViewById(R.id.imageBackButton);

        buttonBack.setOnClickListener(this);
        buttonDialer.setOnClickListener(this);
        buttonEmail.setOnClickListener(this);
        buttonMap.setOnClickListener(this);
        buttonSMS.setOnClickListener(this);
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

        int itemID = view.getId();  //get id of button clicked
        switch (itemID) {
            case R.id.buttonCall:
                Log.d("TAG", "CALL  "+phone);
                // Open Dialer
                break;
            case R.id.buttonText:
                Log.d("TAG", "TEXT  "+phone);
                // Open SMS with suggested message
                break;
            case R.id.buttonEmail:
                Log.d("TAG", "EMAIL  "+email);
                // Open Email with a pre-defined polite email
                break;
            case R.id.buttonMap:
                Log.d("TAG", "MAP  "+meetingPlace);
                // Open Map to defined points in a switch
                break;
            case R.id.imageView:
                Log.d("TAG", "OPEN BOOKFINDER");
                // Open Browser with ISBN
                String url = "https://www.bookfinder.com/search/?isbn="+isbn+"&st=xl&ac=qr&src=recent-m";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.imageBackButton:
                //
                this.finish();
                Log.d("TAG", "BACK BUTTON");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);  // Propagates menu items defined in menu_main
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();  //get id of menu item picked

        switch (itemID) {
            case R.id.menuISBN:
                Log.d("TAG", "ISBN WEB CLICKED");
                break;
            case R.id.menuShare:
                Log.d("TAG", "SHARE MENU CLICKED");
                break;
            case R.id.menuTitle:
                Log.d("TAG", "TITLE WEB CLICKED");
                break;
        } return false;
    }
}

