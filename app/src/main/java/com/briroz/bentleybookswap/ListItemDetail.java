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
import android.widget.Toast;

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
        if (meetingPlace.equals("")) {
            meetingPlace = "No Meeting Place Given";
        }
        textViewLocation.setText(meetingPlace);


    }

    @Override
    public void onClick(View view) {

        int itemID = view.getId();  //get id of button clicked
        switch (itemID) {
            case R.id.buttonCall:
                if (this.phone.equals("N/A")) {   // If the phone exists, do the intent.  If not toast error.
                    // Toast
                } else {
                    Log.d("TAG", "CALL  "+phone);
                    // Open Dialer
                    String phone = "tel:"+ this.phone;
                    Intent i1 = new Intent(Intent.ACTION_VIEW);
                    i1.setData(Uri.parse(phone));
                    try {
                        startActivity(i1);
                    } catch (SecurityException e) {
                        //
                    }
                }

                break;
            case R.id.buttonText:
                if (this.phone.equals("N/A")) {
                    // Toast that Phone # isnt there
                } else {
                    Log.d("TAG", "TEXT  "+ this.phone);
                    // Open SMS with suggested message that includes the book's title.
                    String suggestedText = "Hello, I am contacting you regarding your Bentley Bookswap listing '"+bookTitle+"'.  Is it still available?";
                    String sms = "sms:"+ this.phone;
                    Intent i3 = new Intent(Intent.ACTION_VIEW);
                    i3.putExtra("sms_body", suggestedText);
                    i3.setData(Uri.parse(sms));
                    try {
                        startActivity(i3);
                    } catch (SecurityException e) {
                        //
                    }
                }

                break;
            case R.id.buttonEmail:
                Log.d("TAG", "EMAIL  "+email);
                // Open Email with a pre-defined and polite email
                Intent i4 = new Intent(Intent.ACTION_SEND);
                i4.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                i4.setType("message/rfc822");
                i4.putExtra(Intent.EXTRA_SUBJECT, "Bentley Bookswap Purchase Inquiry: '"+bookTitle+"'  for "+price);
                i4.putExtra(Intent.EXTRA_TEXT, "Dear "+firstName+", \n\n I am contacting you regarding the listing for '"+bookTitle+"' by "+bookAuthor+". Is it still available?  I would like to offer "+price+" if so.  \n\n Thank you,");

                startActivity(Intent.createChooser(i4, "Send Email"));
                break;
            case R.id.buttonMap:
                Log.d("TAG", "MAP  "+meetingPlace);
                // Open Map to defined points in a switch
                switch (meetingPlace) {
                    case "No Meeting Place Given":
                        // empty meeting place, toast error
                        Toast.makeText(getApplicationContext(),"No Meeting Place Given, Please Ask Seller", Toast.LENGTH_LONG).show();
                        break;
                    case "Upper Campus":
                        // Open upper campus
                        String geoLocation ="geo: 42.387720, -71.220219?z=17";
                        Intent i5 = new Intent(Intent.ACTION_VIEW);
                        i5.setData(Uri.parse(geoLocation));
                        if (i5.resolveActivity(getPackageManager()) != null) {
                            startActivity(i5);
                        }
                            break;
                    case "Lower Campus":
                        // open lower
                        String geoLocation1 ="geo: 42.384672, -71.223718?z=18";
                        Intent i6 = new Intent(Intent.ACTION_VIEW);
                        i6.setData(Uri.parse(geoLocation1));
                        if (i6.resolveActivity(getPackageManager()) != null) {
                            startActivity(i6);
                        }
                        break;
                    case "Student Center":
                        //open stu
                        String geoLocation2 ="geo: 42.385964, -71.222777?z=19";
                        Intent i7 = new Intent(Intent.ACTION_VIEW);
                        i7.setData(Uri.parse(geoLocation2));
                        if (i7.resolveActivity(getPackageManager()) != null) {
                            startActivity(i7);
                        }
                        break;
                    case "Library":
                        //open lib
                        String geoLocation3 ="geo: 42.388004, -71.219816?z=19";
                        Intent i8 = new Intent(Intent.ACTION_VIEW);
                        i8.setData(Uri.parse(geoLocation3));
                        if (i8.resolveActivity(getPackageManager()) != null) {
                            startActivity(i8);
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Location Not Recognized, Please Ask Seller", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
            case R.id.imageView:
                Log.d("TAG", "OPEN BOOKFINDER");
                // Open Browser with ISBN
                String url = "https://www.bookfinder.com/search/?isbn="+isbn+"&st=xl&ac=qr&src=recent-m";
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse(url));
                startActivity(i2);
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

