package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

import java.util.ArrayList;
import java.util.HashMap;

    public class BookListActivityByTitle extends AppCompatActivity implements View.OnClickListener {

        private EditText bookTitleSearch;
        private Button bookSearchButton;
        private ListView bookListView;
        private SimpleAdapter simpleAdapter;
        private ProgressDialog loadingDialog;
        private ArrayList<HashMap<String, String>> list= new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_book_list_by_title);  // Set activity view
            bookTitleSearch = findViewById(R.id.editTextTitleSearch);  // Add Search box
            bookListView = findViewById(R.id.titleSortedBookList); // Add book list object to view
            bookSearchButton = findViewById(R.id.buttonSearchTitle);
            bookSearchButton.setOnClickListener(this);
            getItems("");   // Start the JSON retrieval, loading ALL items (empty string)

        }


        private void getItems(final String title) {
            loadingDialog =  ProgressDialog.show(this,"Loading","Please wait",false,true);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbwuldO356FmlWv7RJkxZcusSxomUQX_0oFdw6K8bog1q71mFqM_/exec?action=getItems",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            parseResponseItems(response, title);
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


        private void parseResponseItems(String jsonResponse, String title) {

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
                    if (title.equals("")) {
                        // Load all of the items
                        list.add(listingItem);  // No filtering
                    } else {
                        if (bookTitle.toLowerCase().contains(title.toLowerCase())) {    // Made both lowercase for comparison
                            list.add(listingItem);  // Add the item
                        } else {
                            // Do not add the item if the category does not match
                        }
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            loadingDialog.dismiss();
            simpleAdapter = new SimpleAdapter(this,list,R.layout.list_content, new String[]{"bookTitle","bookAuthor","bookCategory", "price", "itemKey"},new int[]{R.id.ListItemTitle,R.id.ListItemAuthor,R.id.ListItemCategory, R.id.ListItemPrice});
            bookListView.setAdapter(simpleAdapter);

        }

        @Override
        public void onClick(View view) {
            try {
                if (list.size()>0) {
                    list.clear();
                }
                getItems(bookTitleSearch.getText().toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
