package com.briroz.bentleybookswap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class BookListActivityByClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private Spinner classCategory;
    private ListView bookListView;
    private SimpleAdapter simpleAdapter;
    private ProgressDialog loadingDiag;
    private ArrayList<HashMap<String, String>> list= new ArrayList<>();
    // File I/O
    private final String filename = "list.txt";
    private OutputStreamWriter out;
    private String listLength;
    private boolean loaded = false;
    private String strLine;
    // Notifications
    private NotificationManager mNotificationManager;
    private Notification notifyDetails;
    private PendingIntent pendingIntent;
    private static final int SIMPLE_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_by_class);  // Set activity view


        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);   // Setup notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel default",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(channel);
        }
        Intent notifyIntent = new Intent(this, BookListActivityByTitle.class);
        pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        ActionBar actionBar = getSupportActionBar();  // Adds back button to list view
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        classCategory = findViewById(R.id.spinnerClassCategory);  // Add Spinner
        classCategory.setOnItemSelectedListener(this);   // Add listener to update entries when new item selected
        bookListView = findViewById(R.id.classSortedBookList); // Add book list object to view
        bookListView.setOnItemClickListener(this);
        getItems("ALL BOOKS");   // Start the JSON retrieval, loading ALL items
    }


    private void getItems(final String classCategory) {  // Grabs JSON array using the Bookswap Adapter Google Script.
        loadingDiag =  ProgressDialog.show(this,"Loading","Please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbwuldO356FmlWv7RJkxZcusSxomUQX_0oFdw6K8bog1q71mFqM_/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseResponseItems(response, classCategory);  // Parse them into arrays for the list
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 30000;   // times out after waiting 30 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseResponseItems(String jsonResponse, String classCategory) {

        list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResponse);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject job = jarray.getJSONObject(i);

                String itemKey = job.getString("itemKey");
                String firstName = job.getString("firstName");
                String bookTitle = job.getString("bookTitle");
                String bookAuthor = job.getString("bookAuthor");
                String isbn = job.getString("isbn");
                String price = job.getString("price");
                String meetingPlace = job.getString("meetingPlace");
                String phone = job.getString("phone");
                String email = job.getString("email");
                String bookCategory = job.getString("bookCategory");


                HashMap<String, String> listingItem = new HashMap<>();  // Create hashmap object with JSON results per listing item.
                listingItem.put("itemKey",itemKey);
                listingItem.put("firstName", firstName);
                listingItem.put("bookTitle", bookTitle);
                listingItem.put("bookAuthor", bookAuthor);
                listingItem.put("isbn", isbn);
                if (!price.contains("$")) {  // Add a dollar sign if there is not one already
                    price = "$"+price;
                }
                listingItem.put("price", price);
                listingItem.put("meetingPlace", meetingPlace);
                listingItem.put("phone", phone);
                listingItem.put("email", email);
                listingItem.put("bookCategory", bookCategory);
                if (classCategory.equals("") || classCategory.equals("ALL BOOKS")) {
                    // Load all of the items
                    list.add(listingItem);  // No filtering
                } else {
                    if (bookCategory.equals(classCategory)) {
                        list.add(listingItem);  // Add the item
                    } else {
                        // Do not add the item if the category does not match
                    }
                }
            }
            notifyListSize();  // Write the list size after all JSON objects retrieved.
        } catch (JSONException e) {
            e.printStackTrace();
        }


        loadingDiag.dismiss();
        simpleAdapter = new SimpleAdapter(this,list,R.layout.list_content, new String[]{"bookTitle","bookAuthor","bookCategory","price", "isbn", "itemKey"},new int[]{R.id.ListItemTitle,R.id.ListItemAuthor,R.id.ListItemCategory, R.id.ListItemPrice});
        bookListView.setAdapter(simpleAdapter);

    }



    private void notifyListSize() {
        if (loaded == false) {   // Only writes on the first load.  OnCreate would not work, as there was a delay after getItems required to get the list.size()
            File file = new File(getApplicationContext().getFilesDir(), "list.txt");
            if (!file.exists()) {     // Checking if the file exists before opening the inputstream.  If not found, test list objects will be added.
                Log.d("TAG", "FILE NOT FOUND, starting with testing list objects");
                Toast.makeText(getApplicationContext(), "First time boot, writing list length to file.", Toast.LENGTH_LONG).show();    // Displays a toast when no text file is found
                try {
                    listLength = list.size()+"";
                    String listLengthString = listLength.toString();
                    Log.d("TAG", listLength);

                    out = new OutputStreamWriter(openFileOutput(filename, MODE_PRIVATE));   // Writes over entire .txt file each time saved
                    out.write(listLength);
                    out.close();    //close output stream

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {   // Only opens the stream if the file was located.
                Log.d("TAG", "FILE EXISTS");
                try {
                    FileInputStream fis = openFileInput("list.txt");
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    strLine = br.readLine();
                    fis.close();
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    listLength = list.size() + "";
                    int listInt = list.size();
                    int loadedListInt = Integer.parseInt(strLine);
                    if (listInt != loadedListInt) {  // If there is a different number of books compared to the last time loaded.
                        if (listInt > loadedListInt) {  // If the current list is larger than last time
                            int difference = (listInt-loadedListInt);  // Compute the difference between the two
                            //Toast.makeText(getApplicationContext(), "There are "+difference+" more books as last time", Toast.LENGTH_LONG).show();    // Displays a toast when no text file is found
                            notifyDetails =
                                    new Notification.Builder(this)
                                            .setContentIntent(pendingIntent)  //set PendingIntent
                                            .setChannelId("default")
                                            .setSmallIcon(R.drawable.bookcover)
                                            .setContentTitle("More Books")   //set Notification text and icon
                                            .setContentText("There are "+difference+" more books than the last time visited")
                                            .setTicker("Change in bookstore")            //set status bar text
                                            .setWhen(System.currentTimeMillis())    //timestamp when event occurs
                                            .setAutoCancel(true)     //cancel Notification after clicking on it
                                            .setVibrate(new long[]{1000, 1000, 1000, 1000})     //set Android to vibrate when notified
                                            .build();
                            mNotificationManager.notify(SIMPLE_ID, notifyDetails);

                        } else {  // If the current list is smaller than last time
                            int difference = (loadedListInt-listInt);
                            //Toast.makeText(getApplicationContext(), "There are "+difference+" less books loaded compared to the last time", Toast.LENGTH_LONG).show();    // Displays a toast when no text file is found
                            notifyDetails =
                                    new Notification.Builder(this)
                                            .setContentIntent(pendingIntent)  //set PendingIntent
                                            .setChannelId("default")
                                            .setSmallIcon(R.drawable.bookcover)
                                            .setContentTitle("Less Books")   //set Notification text and icon
                                            .setContentText("There are "+difference+" fewer books than the last time visited")
                                            .setTicker("Change in bookstore")            //set status bar text
                                            .setWhen(System.currentTimeMillis())    //timestamp when event occurs
                                            .setAutoCancel(true)     //cancel Notification after clicking on it
                                            .setVibrate(new long[]{1000, 1000, 1000, 1000})     //set Android to vibrate when notified
                                            .build();
                            mNotificationManager.notify(SIMPLE_ID, notifyDetails);
                        }
                    } else {
                        //Toast.makeText(getApplicationContext(), "Same number as last time", Toast.LENGTH_LONG).show();    // Displays a toast when no text file is found
                        notifyDetails =
                                new Notification.Builder(this)
                                        .setContentIntent(pendingIntent)  //set PendingIntent
                                        .setChannelId("default")
                                        .setSmallIcon(R.drawable.bookcover)
                                        .setContentTitle("Same Books")   //set Notification text and icon
                                        .setContentText("There are the same number of books as last time")
                                        .setTicker("No change in bookstore")            //set status bar text
                                        .setWhen(System.currentTimeMillis())    //timestamp when event occurs
                                        .setAutoCancel(true)     //cancel Notification after clicking on it
                                        .setVibrate(new long[]{1000, 1000, 1000, 1000})     //set Android to vibrate when notified
                                        .build();
                        mNotificationManager.notify(SIMPLE_ID, notifyDetails);

                    }
                    out = new OutputStreamWriter(openFileOutput(filename, MODE_PRIVATE));   // Writes over entire .txt file each time saved
                    out.write(listLength);
                    out.close();    //close output stream
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            loaded = true;  // Notify further JSON loads not to store shorter, searched list sizes.
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            if (list.size()>0) {
                list.clear();
            } else {
                loadingDiag.dismiss();  // Kills persistent dialog on starting load, when list is null
            }
            getItems(classCategory.getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {  // Passes on the book's information to the next Detail activity
        Log.d("TAG", "CLICKED");
        Intent intent = new Intent(this, ListItemDetail.class);
        HashMap<String,String> map =(HashMap)adapterView.getItemAtPosition(i);
        String itemKey = map.get("itemKey").toString();
        String firstName = map.get("firstName").toString();
        String bookTitle = map.get("bookTitle").toString();
        String bookAuthor = map.get("bookAuthor").toString();
        String isbn = map.get("isbn");
        if (isbn.equals("")) {  // If the ISBN is Empty, add a N/A
            isbn = "N/A";
        }
        String price = map.get("price");
        if (!price.contains("$")) {  // Add a dollar sign if there is not one already
            price = "$"+price;
        }
        String meetingPlace = map.get("meetingPlace");
        String phone = map.get("phone");
        if (phone.equals("")) {
            phone = "N/A";
        }
        String email = map.get("email");
        String bookCategory = map.get("bookCategory");


        intent.putExtra("itemKey",itemKey);
        intent.putExtra("firstName", firstName);
        intent.putExtra("bookTitle",bookTitle);
        intent.putExtra("bookAuthor", bookAuthor);
        intent.putExtra("isbn", isbn);
        intent.putExtra("price",price);
        intent.putExtra("meetingPlace", meetingPlace);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        intent.putExtra("bookCategory",bookCategory);


        startActivity(intent);  // Open the detail view
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();  //get id of menu item picked
        if (itemID == android.R.id.home) {
            this.finish();  // Go back
        }
        return false;
    }

}
