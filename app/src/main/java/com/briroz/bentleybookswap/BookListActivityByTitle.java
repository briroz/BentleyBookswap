package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

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

    public class BookListActivityByTitle extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

        private EditText bookTitleSearch;
        private Button bookSearchButton;
        private ListView bookListView;
        private SimpleAdapter simpleAdapter;
        private ProgressDialog loadingDiag;
        private ArrayList<HashMap<String, String>> list;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_book_list_by_class);  // Set activity view
            bookTitleSearch = findViewById(R.id.editTextTitleSearch);  // Add Search box
            classCategory.setOnItemSelectedListener(this);   // Add listener to update entries when new item selected
            bookListView = findViewById(R.id.classSortedBookList); // Add book list object to view

            getItems("");   // Start the JSON retrieval, loading ALL items
        }


        private void getItems(final String s) {
            loadingDiag =  ProgressDialog.show(this,"Loading","please wait",false,true);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbwuldO356FmlWv7RJkxZcusSxomUQX_0oFdw6K8bog1q71mFqM_/exec?action=getItems",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            parseResponseItems(response, s);
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
                    listingItem.put("price", price);
                    listingItem.put("meetingPlace", meetingPlace);
                    listingItem.put("phone", phone);
                    listingItem.put("email", email);
                    listingItem.put("bookCategory", bookCategory);
                    if (classCategory.equals("")) {
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
            } catch (JSONException e) {
                e.printStackTrace();
            }


            simpleAdapter = new SimpleAdapter(this,list,R.layout.list_content, new String[]{"bookTitle","bookAuthor","bookCategory","itemKey"},new int[]{R.id.ListItemTitle,R.id.ListItemAuthor,R.id.ListItemCategory});
            bookListView.setAdapter(simpleAdapter);
            loadingDiag.dismiss();

        }




        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                if (list.size()>0) {
                    list.clear();
                }
                getItems(classCategory.getSelectedItem().toString());
                //    BookListActivityByClass.this.simpleAdapter.getFilter().filter(classCategory.getSelectedItem().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }













    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_by_title);
        bookList = findViewById(R.id.titleSortedBookList);
        bookTitleSearch = findViewById(R.id.editTextTitleSearch);

    }
}
