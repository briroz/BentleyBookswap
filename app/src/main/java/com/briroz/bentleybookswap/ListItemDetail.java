package com.briroz.bentleybookswap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ListItemDetail extends AppCompatActivity implements View.OnClickListener, OnInitListener {


    TextView textViewTitle, textViewAuthor, textViewPrice, textViewCategory, textViewIsbn, textViewName, textViewPhone, textViewEmail, textViewLocation;
    ImageView imageView;
    Button buttonMap, buttonDialer, buttonSMS, buttonEmail;
    String itemKey, firstName, bookTitle, bookAuthor, isbn, price, meetingPlace, phone, email, bookCategory;
    private TextToSpeech speaker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_item_detail);

        ActionBar actionBar = getSupportActionBar();  // Adds back button to Detail view, back button goes to either title list or class list depending which was used.
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        speaker = new TextToSpeech(this, this);

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

        buttonDialer.setOnClickListener(this);
        buttonEmail.setOnClickListener(this);
        buttonMap.setOnClickListener(this);
        buttonSMS.setOnClickListener(this);
        imageView.setOnClickListener(this);
        textViewTitle.setOnClickListener(this);   // For speaking the title when clicked
        textViewAuthor.setOnClickListener(this);  // ^^ Author

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


    //speak methods will send text to be spoken
    public void speak(String output){
        speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null, "ID 0");
    }



    public void onInit(int status) {                 // Implements TextToSpeech.OnInitListener
        if (status == TextToSpeech.SUCCESS) {        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.

            int result = speaker.setLanguage(Locale.US); // Set preferred language to US english.
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                Log.e("TAG", "Language is not available.");
            } else {
                // The TTS engine has been successfully initialized
                Log.i("TAG", "TTS Initialization successful.");
            }
        } else {
            // Initialization failed.
            Log.e("TAG", "Could not initialize TextToSpeech.");
        }
    }

    // on destroy, shutdown speaker
    public void onDestroy(){

        // shut down TTS engine
        if(speaker != null){
            speaker.stop();
            speaker.shutdown();
        }
        super.onDestroy();
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
            case R.id.textViewTitle:
                String titleSpeak = textViewTitle.getText().toString();
                speak(titleSpeak);
                break;
            case R.id.textViewAuthor:
                String authorSpeak = textViewAuthor.getText().toString();
                speak(authorSpeak);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("");  // Blank out title, buttons take up all of the titlebar
//        actionBar.setHomeButtonEnabled(true);
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
            case android.R.id.home:
                this.finish();  // Go back when back button is pressed
                break;
        } return false;
    }
}

