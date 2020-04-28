package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class SellBookActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, title, author, isbn, price, phone, email;
    private Spinner category, location;
    private Button submit;
    private CheckBox box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);

        name = findViewById(R.id.editTextName);
        title = findViewById(R.id.editTextTitle);
        author = findViewById(R.id.editTextAuthor);
        isbn = findViewById(R.id.editTextISBN);
        price = findViewById(R.id.editTextPrice);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.editTextEmail);
        location = findViewById(R.id.spinnerLocation);
        category = findViewById(R.id.spinnerBookCategory);
        submit = findViewById(R.id.buttonSubmit);
        box = findViewById(R.id.checkBox);
        submit.setOnClickListener(this);

    }

    private void   addItemToSheet() {  // Function submits the approved parameters to the google script which will then be added to the Google Sheet

        final ProgressDialog loading = ProgressDialog.show(this, "Adding Item", "Please wait");
        final String itemFname = name.getText().toString().trim();
        final String itemTitle = title.getText().toString().trim();
        final String itemAuthor = author.getText().toString().trim();
        final String itemIsbn = isbn.getText().toString().trim();
        final String itemPrice = price.getText().toString().trim();
        final String itemPhone = phone.getText().toString().trim();
        final String itemEmail = email.getText().toString().trim();
        final String itemLocation = location.getSelectedItem().toString().trim();
        final String itemCategory = category.getSelectedItem().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwuldO356FmlWv7RJkxZcusSxomUQX_0oFdw6K8bog1q71mFqM_/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(SellBookActivity.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> itemParams = new HashMap<>();

                // Here we pass params
                itemParams.put("action","addItem"); // Tells the script which function to run
                itemParams.put("fName",itemFname);  // Adds the form's fields as parameters
                itemParams.put("bookTitle",itemTitle);
                itemParams.put("bookAuthor",itemAuthor);
                itemParams.put("isbn",itemIsbn);
                itemParams.put("price",itemPrice);
                itemParams.put("meetingPlace",itemLocation);
                itemParams.put("phone",itemPhone);
                itemParams.put("email",itemEmail);
                itemParams.put("bookCategory",itemCategory);
                return itemParams;
            }
        };

        int socketTimeOut = 30000;// timeout is 30 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }


    @Override
    public void onClick(View v) {
        boolean submittable = box.isChecked() && !(name.getText().toString().equals("") || title.getText().toString().equals("") || author.getText().toString().equals("") || price.getText().toString().equals("") || email.getText().toString().equals("") || category.getSelectedItem().toString().equals("") );  // Validates fields required on a basic level (are they empty or not)
        if (box.isChecked()) {
            Log.d("TAG", "Checked: "+submittable);
            if (submittable == true) {
                // submit
                addItemToSheet();
            } else {
                Toast.makeText(getApplicationContext(), "Please Fill Out The Required Fields", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "Please Accept Agreement", Toast.LENGTH_SHORT).show();
        }
    }
}
